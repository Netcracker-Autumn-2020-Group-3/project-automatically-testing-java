package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.NotificationDAO;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseExecutionMapper;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:queries/postgres.properties")
@Repository
public class NotificationDAOImpl implements NotificationDAO {

    @Value("${set.notifications}")
    String INSERT_NOTIFICATIONS;
    @Value("${get.notification.test.case_execution}")
    String GET_NOTIFICATION;



    private final JdbcTemplate jdbcTemplate;
    private final TestCaseExecutionMapper executionMapper;

    public NotificationDAOImpl(JdbcTemplate jdbcTemplate, TestCaseExecutionMapper executionMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.executionMapper = executionMapper;
    }

    @Override
    public void insertNotifications(long testCaseId, long testCaseExecutionId) {
        jdbcTemplate.update(INSERT_NOTIFICATIONS,testCaseId, testCaseExecutionId );
    }

    @Override
    public List<TestCaseExecution> getTestCaseExecutions(long userId) {
        return jdbcTemplate.queryForStream(GET_NOTIFICATION, executionMapper, userId).collect(Collectors.toList());
    }
}
