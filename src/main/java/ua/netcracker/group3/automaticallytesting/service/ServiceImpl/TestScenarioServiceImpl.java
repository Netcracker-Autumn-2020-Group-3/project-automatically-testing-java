package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;

import java.util.List;

@Service
public class TestScenarioServiceImpl implements TestScenarioService {

    private TestScenarioDAO testScenarioDAO;

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
    public List<TestScenario> getAll(){
        return testScenarioDAO.getAll();
    }

}
