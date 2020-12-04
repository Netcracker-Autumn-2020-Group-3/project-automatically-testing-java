package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseStep;

import java.util.List;

public interface TestCaseDAO {
    long insert(TestCase testCase);
    List<TestCaseStep> getTestCaseSteps(Long testCaseId);
}
