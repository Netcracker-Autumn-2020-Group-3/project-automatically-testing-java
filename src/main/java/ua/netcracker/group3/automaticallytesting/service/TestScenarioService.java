package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.TestScenario;

import java.util.List;

public interface TestScenarioService {

    void updateTestScenario(TestScenario testScenario);

    void saveTestScenario(TestScenario testScenario);

    List<TestScenario> getAll();
}
