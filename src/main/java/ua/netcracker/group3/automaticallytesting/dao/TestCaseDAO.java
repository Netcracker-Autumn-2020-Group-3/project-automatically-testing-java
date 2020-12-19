package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.*;

import java.util.List;


import java.util.List;

public interface TestCaseDAO {

    long insert(TestCase testCase);
    List<TestCaseStep> getTestCaseSteps(Long testCaseId);
    List<TestCaseUpd> getTestCases();
    List<TestCaseUpd> getTestCasesPageSorted(Long projectID, String orderByLimitOffsetWithValues, String name);
    Integer countUsers();

    List<TestCaseTopSubscribed> getTopFiveSubscribedTestCases();


    void update(Long testCaseId, String newTestCaseName);

    void addSubscriber(Long testCaseId, Long userId);

    Boolean isFollowedByUser(Long testCaseId, Long userId);

    void removeSubscriber(Long testCaseId, Long userId);
}
