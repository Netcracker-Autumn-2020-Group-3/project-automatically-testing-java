package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.TestScenario;

import java.util.List;

public interface TestScenarioDAO {

    void updateTestScenarioById(TestScenario testScenario);

    void saveTestScenario(TestScenario testScenario);

    List<TestScenario> getAllTestScenarios();

}
