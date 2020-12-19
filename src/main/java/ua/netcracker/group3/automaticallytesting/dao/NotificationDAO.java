package ua.netcracker.group3.automaticallytesting.dao;


import ua.netcracker.group3.automaticallytesting.dto.NotificationDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.model.User;

import java.util.List;

public interface NotificationDAO {

    void insertNotifications(long testCaseId, long testCaseExecutionId);

    List<TestCaseExecution> getTestCaseExecutions(long userId);

    List<User> getUsersId(long testCaseExecutionId);

    List<TestCaseExecution> getRecentNotifications(long testCaseId, long testCaseExecutionId);

    List<NotificationDto> getNotificationsByUser(long userId);

    Integer amountOfNotifications(long userId);

    void deleteNotification(long testCaseExecutionId, long userId);
}

