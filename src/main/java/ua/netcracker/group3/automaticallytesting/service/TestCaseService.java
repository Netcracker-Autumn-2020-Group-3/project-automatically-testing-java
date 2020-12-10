package ua.netcracker.group3.automaticallytesting.service;

import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dto.CreateUpdateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;

import java.util.List;

import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

public interface TestCaseService {
    @Transactional
    void createTestCase(CreateUpdateTestCaseDto createUpdateTestCaseDto, Long userId);

    List<ScenarioStepDto> getTestScenarioStep(Long testCaseId);

    TestCaseDto getTestCase(Long testCaseId);

    List<TestCaseUpd> getTestCases(Pageable pageable, String name);
    List<TestCaseUpd> getAllTestCases();
    Integer countPages(Integer pageSize);


    void updateTestCase(CreateUpdateTestCaseDto createUpdateTestCaseDto);
}
