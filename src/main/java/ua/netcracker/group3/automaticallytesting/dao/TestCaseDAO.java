package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;


import java.util.List;

public interface TestCaseDAO {
    long insert(TestCase testCase);
    List<TestCaseUpd> getTestCases();
}
