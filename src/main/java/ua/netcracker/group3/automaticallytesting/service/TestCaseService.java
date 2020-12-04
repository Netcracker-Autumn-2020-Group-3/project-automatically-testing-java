package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;

import java.util.List;

public interface TestCaseService {
    List<ScenarioStepDto> getTestScenarioStep(Long testCaseId);

    TestCaseDto getTestCase(Long testCaseId);
}
