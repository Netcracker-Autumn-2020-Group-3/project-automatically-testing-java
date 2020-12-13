package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.sql.Date;
import java.util.List;

public interface TestCaseExecutionDAO {
    List<TestCaseExecution> getAllTestCaseExecutions();
    List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber();
    Long createTestCaseExecution(long testCaseId, long userId);
    void updateTestCaseExecution(Enum status, long testCaseExecutionId);

    List<TestCaseExecutionsCountsByStartDatesDto> getExecutionsByStartDate(Date fromDate, Date tillDate);

    List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecution();
}
