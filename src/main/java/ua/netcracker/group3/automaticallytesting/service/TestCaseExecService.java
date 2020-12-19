package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.util.List;

public interface TestCaseExecService {

    List<TestCaseExecution> getAllTestCaseExecutions();

    Integer countTestCaseExecutions(String testCaseName, String projectName, String status);

    List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber(long limit,long offset, String orderBy, String orderByClause, String testCaseName, String projectName, String status);

    Long createTestCaseExecution(Long testCaseId, long userId);

    void updateTestCaseExecution(String status, long testCaseExecutionId);

    List<TestCaseExecutionsCountsByStartDatesDto> getExecutionsByDatesForLastDays(Integer daysFromToday);

    List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecution();
}
