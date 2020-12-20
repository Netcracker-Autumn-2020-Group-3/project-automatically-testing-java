package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.ActionInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.dao.CompoundInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDtoWithIdNameArchived;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioItemDto;
import ua.netcracker.group3.automaticallytesting.model.CompoundActionWithActionIdAndPriority;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.util.Pagination;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestScenarioServiceImpl implements TestScenarioService {

    private final TestScenarioDAO testScenarioDAO;
    private final Pagination pagination;
    private final List<String> TEST_SCENARIO_TABLE_FIELDS = Arrays.asList("id", "name");
    private final CompoundInstanceDAO compoundInstanceDAO;
    private final CompoundDAO compoundDAO;
    private final ActionInstanceDAO actionInstanceDAO;

    private String replaceNullsForSearch(String val) {
        return val == null ? "%" : val;
    }


    public TestScenarioServiceImpl(TestScenarioDAO testScenarioDAO, Pagination pagination, CompoundInstanceDAO compoundInstanceDAO, CompoundDAO compoundDAO, ActionInstanceDAO actionInstanceDAO) {
        this.testScenarioDAO = testScenarioDAO;
        this.pagination = pagination;
        this.compoundInstanceDAO = compoundInstanceDAO;
        this.compoundDAO = compoundDAO;
        this.actionInstanceDAO = actionInstanceDAO;
    }

    @Override
    public List<TestScenario> getTestScenarioById(long id) {
        List<TestScenario> testScenarios = testScenarioDAO.getTestScenarioById(id);
        if(testScenarios.isEmpty()) {
            log.warn("IN getTestScenarioById - no test scenario found by id: {}", id);
            return testScenarios;
        }
        log.info("IN getTestScenarioById - test scenario: {} found by id: {}", testScenarios, id);
        return testScenarios;
    }

    @Override
    public boolean updateTestScenario(TestScenarioDtoWithIdNameArchived testScenarioDto) {
        String testScenarioName = testScenarioDto.getName();
        if(testScenarioDAO.checkExistTestScenarioByName(testScenarioName)) {
            log.warn("IN updateTestScenario - test scenario already exists with name: {}", testScenarioName);
            return false;
        }
        TestScenario testScenario = new TestScenario(
                testScenarioDto.getId(),
                testScenarioDto.getName(),
                testScenarioDto.isArchived()
        );
        testScenarioDAO.updateTestScenarioById(testScenario);
        log.info("IN updateTestScenario - test scenario: {} is updated", testScenarioDto);
        return true;
    }

    @Transactional
    @Override
    public boolean saveTestScenario(TestScenarioDto testScenarioDto) {
        String testScenarioName = testScenarioDto.getName();
        if(testScenarioDAO.checkExistTestScenarioByName(testScenarioName)) {
            log.warn("IN saveTestScenario - test scenario already exists with name: {}", testScenarioName);
            return false;
        }
        long testScenarioId = testScenarioDAO.saveTestScenario(testScenarioDto);
        List<TestScenarioItemDto> actionsWithoutCompoundInstanceId =
                getItemsByType("Action", testScenarioDto.getItems());
        actionInstanceDAO.saveActionInstancesWithoutCompoundInstanceId(
                actionsWithoutCompoundInstanceId, testScenarioId
        );
        log.info("IN saveTestScenario - action instances: {} are saved without compound instance id", actionsWithoutCompoundInstanceId);
        List<TestScenarioItemDto> compounds =
                getItemsByType("Compound", testScenarioDto.getItems());
        for(TestScenarioItemDto compound : compounds) {
            long compoundId = compoundInstanceDAO.saveCompoundInstanceAndGetGeneratedId(compound, testScenarioId);
            List<TestScenarioItemDto> actions = compound.getItems();
            actionInstanceDAO.saveActionInstancesWithCompoundInstanceId(
                    actions,
                    testScenarioId,
                    compoundId
            );
            log.info("IN saveTestScenario - action instances: {} are saved with compound instance id: {}", actions, compoundId);
        }
        log.info("IN saveTestScenario - test scenario with name: {} is saved", testScenarioName);
        return true;
    }

    @Override
    public List<CompoundActionWithActionIdAndPriority> getAllCompoundActionsByCompoundId(long id) {
        List<CompoundActionWithActionIdAndPriority> actions =
                compoundDAO.findAllCompoundActionsWithActionIdAndPriorityByCompoundId(id);
        if(actions.isEmpty()) {
            log.warn("IN getAllCompoundActionsByCompoundId - no actions found with compound is: {}", id);
            return actions;
        }
        log.info("IN getAllCompoundActionsByCompoundId - actions: {} are found with compound id: {}", actions, id);
        return actions;
    }

    @Override
    public List<TestScenario> getTestScenarios(Pageable pageable, String name) {
        pageable = pagination.replaceNullsUserPage(pageable);
        pagination.validate(pageable,TEST_SCENARIO_TABLE_FIELDS);
        return testScenarioDAO.getTestScenariosPageSorted(pagination.formSqlPostgresPaginationPiece(pageable),
                replaceNullsForSearch(name));
    }
    @Override
    public List<TestScenario> getAll(){
        return testScenarioDAO.getAll();
    }

    private List<TestScenarioItemDto> getItemsByType(String type, List<TestScenarioItemDto> items) {
        return items.stream()
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());

    }

    @Override
    public Integer countPages(Integer pageSize) {
        return pagination.countPages(testScenarioDAO.countUsers(), pageSize);
    }
}
