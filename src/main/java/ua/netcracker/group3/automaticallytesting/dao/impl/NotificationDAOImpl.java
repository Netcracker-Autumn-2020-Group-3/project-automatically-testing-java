package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.NotificationDAO;
@PropertySource("classpath:queries/postgres.properties")
@Repository
public class NotificationDAOImpl implements NotificationDAO {

    @Value("${set.notification}")
    String INSERT_NOTIFICATIONS;

    private final JdbcTemplate jdbcTemplate;

    public NotificationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertNotifications(long testCaseId, long testCaseExecutionId) {
        jdbcTemplate.update(INSERT_NOTIFICATIONS,testCaseId, testCaseExecutionId );
    }
}
