package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;

import java.util.List;

public interface TestScenarioService {

    void updateTestScenario(TestScenario testScenario);

    void saveTestScenario(TestScenarioDto testScenarioDto);

    List<TestScenario> getAll();
}
