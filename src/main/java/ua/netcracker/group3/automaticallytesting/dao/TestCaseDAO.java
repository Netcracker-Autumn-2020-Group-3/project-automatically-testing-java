package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseStep;

import java.util.List;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;


import java.util.List;

public interface TestCaseDAO {

    long insert(TestCase testCase);
    List<TestCaseStep> getTestCaseSteps(Long testCaseId);
    List<TestCaseUpd> getTestCases();
    List<TestCaseUpd> getTestCasesPageSorted(String orderByLimitOffsetWithValues, String name);
    Integer countUsers();


    void update(Long testCaseId, String newTestCaseName);
}
