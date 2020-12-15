package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.NotificationDAO;
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
    String INSERT_NOTIFICATIONS;
    @Value("${get.notification.test.case_execution}")
    String GET_NOTIFICATION;
    @Value("${get.notification.users}")
    String GET_USERS_ID;



    private final JdbcTemplate jdbcTemplate;
    private final TestCaseExecutionByIdMapper executionByIdMapper;
    private final UserByIdMapper userByIdMapper;

    public NotificationDAOImpl(JdbcTemplate jdbcTemplate, TestCaseExecutionByIdMapper executionByIdMapper, UserByIdMapper userByIdMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.executionByIdMapper = executionByIdMapper;
        this.userByIdMapper = userByIdMapper;
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
}
