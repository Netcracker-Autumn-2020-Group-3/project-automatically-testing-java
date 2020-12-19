package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.NotificationDAO;
import ua.netcracker.group3.automaticallytesting.dto.NotificationDto;
import ua.netcracker.group3.automaticallytesting.mapper.NotificationMapper;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseExecutionByIdMapper;
import ua.netcracker.group3.automaticallytesting.mapper.UserByIdMapper;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.model.User;

import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:queries/postgres.properties")
@Repository
public class NotificationDAOImpl implements NotificationDAO {

    @Value("${set.notifications}")
    private String INSERT_NOTIFICATIONS;
    @Value("${get.notification.test.case_execution}")
    private String GET_NOTIFICATION;
    @Value("${get.notification.users}")
    private String GET_USERS_ID;
    @Value("${get.recent.notifications}")
    private String GET_RECENT_NOTIFICATIONS;
    @Value("${get.notifications.by.user}")
    private String GET_NOTIFICATIONS_BY_USER;
    @Value("${get.amount.of.notifications}")
    private String GET_AMOUNT_OF_NOTIFICATIONS;
    @Value("${delete.notification}")
    private String DELETE_NOTIFICATION;



    private final JdbcTemplate jdbcTemplate;
    private final TestCaseExecutionByIdMapper executionByIdMapper;
    private final UserByIdMapper userByIdMapper;
    private final NotificationMapper notificationMapper;

    public NotificationDAOImpl(JdbcTemplate jdbcTemplate, TestCaseExecutionByIdMapper executionByIdMapper,
                               UserByIdMapper userByIdMapper, NotificationMapper notificationMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.executionByIdMapper = executionByIdMapper;
        this.userByIdMapper = userByIdMapper;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public void insertNotifications(long testCaseId, long testCaseExecutionId) {
        jdbcTemplate.update(INSERT_NOTIFICATIONS,testCaseId, testCaseExecutionId );
    }

    @Override
    public List<TestCaseExecution> getTestCaseExecutions(long userId) {
        return jdbcTemplate.queryForStream(GET_NOTIFICATION, executionByIdMapper, userId).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersId(long testCaseExecutionId) {
        return jdbcTemplate.queryForStream(GET_USERS_ID, userByIdMapper, testCaseExecutionId).collect(Collectors.toList());
    }

    @Override
    public List<TestCaseExecution> getRecentNotifications(long testCaseId, long testCaseExecutionId) {
        return jdbcTemplate.queryForStream(GET_RECENT_NOTIFICATIONS, executionByIdMapper, testCaseId, testCaseExecutionId).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getNotificationsByUser(long userId) {
        return jdbcTemplate.queryForStream(GET_NOTIFICATIONS_BY_USER, notificationMapper, userId).collect(Collectors.toList());
    }

    @Override
    public Integer amountOfNotifications(long userId) {
        return jdbcTemplate.queryForObject(GET_AMOUNT_OF_NOTIFICATIONS, Integer.class, userId);
    }

    @Override
    public void deleteNotification(long testCaseExecutionId, long userId) {
        jdbcTemplate.update(DELETE_NOTIFICATION, testCaseExecutionId, userId);
    }
}
