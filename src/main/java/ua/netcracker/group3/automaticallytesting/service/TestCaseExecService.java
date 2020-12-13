package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.util.List;

public interface TestCaseExecService {

    List<TestCaseExecution> getAllTestCaseExecutions();

    List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber();

    Long createTestCaseExecution(Long testCaseId, long userId);

    void updateTestCaseExecution(Enum status, long testCaseExecutionId);

    List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecution();
}