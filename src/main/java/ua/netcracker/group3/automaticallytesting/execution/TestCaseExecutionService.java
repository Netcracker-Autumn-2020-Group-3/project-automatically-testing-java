package ua.netcracker.group3.automaticallytesting.execution;

import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.execution.action.ContextVariable;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.util.List;
import java.util.Map;

public interface TestCaseExecutionService {

  /*  Map<Long, ContextVariable> executeTestCase(TestCaseDto testCaseDto,Long testCaseExecutionId);*/
    List<String> executeTestCase(TestCaseDto testCaseDto, Long testCaseExecutionId);

}
