package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.dao.CompoundInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;
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
    public void updateTestScenario(TestScenario testScenario) {
        testScenarioDAO.updateTestScenarioById(testScenario);
    }

    @Override
    public boolean saveTestScenario(TestScenarioDto testScenarioDto) {

        String testScenarioName = testScenarioDto.getName();

        if(testScenarioDAO.checkExistTestScenarioByName(testScenarioName)) {
            return false;
        }

        long testScenarioId = testScenarioDAO.saveTestScenario(testScenarioDto);

        List<TestScenarioItemDto> actionsWithoutCompoundInstanceId =
                getItemsByType("Action", testScenarioDto.getItems());

        actionInstanceDAO.saveActionInstancesWithoutCompoundInstanceId(
                actionsWithoutCompoundInstanceId, testScenarioId
        );
        List<TestScenarioItemDto> compounds =
                getItemsByType("Compound", testScenarioDto.getItems());

        for(TestScenarioItemDto compound : compounds) {

            long compoundId = compoundInstanceDAO.saveCompoundInstanceAndGetGeneratedId(compound, testScenarioId);

            List<CompoundActionWithActionIdAndPriority> compoundActions =
                    compoundDAO.findAllCompoundActionsWithActionIdAndPriorityByCompoundId(compound.getId());

            List<TestScenarioItemDto> actions =
                    getTestScenarioItemDtoFromCompoundActions(compoundActions);

            actionInstanceDAO.saveActionInstancesWithCompoundInstanceId(
                    actions,
                    testScenarioId,
                    compoundId
            );
        }

        return true;

    }

    @Override
    public boolean checkTestScenarioExistsByName(String name) {
        return testScenarioDAO.checkExistTestScenarioByName(name);
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

    private List<TestScenarioItemDto> getTestScenarioItemDtoFromCompoundActions(
            List<CompoundActionWithActionIdAndPriority> compoundActions
    ) {
        return compoundActions.stream()
                .map(c -> new TestScenarioItemDto(
                        c.getActionId(),
                        c.getPriority()
                )).collect(Collectors.toList());
    }

    @Override
    public Integer countPages(Integer pageSize) {
        return pagination.countPages(testScenarioDAO.countUsers(), pageSize);
    }
}
