package ua.netcracker.group3.automaticallytesting.dao;


import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.util.List;

public interface NotificationDAO {

    void insertNotifications(long testCaseId, long testCaseExecutionId);

    List<TestCaseExecution> getTestCaseExecutions(long userId);
}
