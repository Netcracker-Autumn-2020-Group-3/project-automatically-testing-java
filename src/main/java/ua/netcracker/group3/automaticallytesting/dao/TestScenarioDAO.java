package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;

import java.util.List;

public interface TestScenarioDAO {

    void updateTestScenarioById(TestScenario testScenario);

    long saveTestScenario(TestScenarioDto testScenarioDto);

    List<TestScenario> getAll();

}
