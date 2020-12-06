package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.TestCase;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dto.CreateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;

import java.util.List;

import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface TestCaseService {
    @Transactional
    void createTestCase(CreateTestCaseDto createTestCaseDto, Long userId);

    List<ScenarioStepDto> getTestScenarioStep(Long testCaseId);

    TestCaseDto getTestCase(Long testCaseId);

    List<TestCaseUpd> getTestCases(Pageable pageable, String name);
    List<TestCaseUpd> getAllTestCases();
    Integer countPages(Integer pageSize);


}
