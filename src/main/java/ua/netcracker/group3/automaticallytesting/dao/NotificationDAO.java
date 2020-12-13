package ua.netcracker.group3.automaticallytesting.dao;

public interface NotificationDAO {

    void insertNotifications(long testCaseId, long testCaseExecutionId);
}
