package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.util.Pagination;

import java.util.Arrays;
import java.util.List;

@Service
public class TestScenarioServiceImpl implements TestScenarioService {

    private TestScenarioDAO testScenarioDAO;
    Pagination pagination;
    private final List<String> TEST_SCENARIO_TABLE_FIELDS = Arrays.asList("id", "name");

    private String replaceNullsForSearch(String val) {
        return val == null ? "%" : val;
    }


    public TestScenarioServiceImpl(TestScenarioDAO testScenarioDAO) {
        this.testScenarioDAO = testScenarioDAO;
    }

    @Override
    public void updateTestScenario(TestScenario testScenario) {
        testScenarioDAO.updateTestScenarioById(testScenario);
    }

    @Override
    public void saveTestScenario(TestScenario testScenario) {
        testScenarioDAO.saveTestScenario(testScenario);
    }

    @Override
    public List<TestScenario> getTestScenarios(Pageable pageable, String name) {
        pageable = pagination.replaceNullsUserPage(pageable);
        pagination.validate(pageable,TEST_SCENARIO_TABLE_FIELDS);
        return testScenarioDAO.getTestScenariosPageSorted(pagination.formSqlPostgresPaginationPiece(pageable),
                replaceNullsForSearch(name));
    }


}
