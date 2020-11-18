package ua.netcracker.group3.automaticallytesting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.controller.SqlConstant;
import ua.netcracker.group3.automaticallytesting.model.User;

import java.util.List;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String getEmail(Long userId) {
        return jdbcTemplate.queryForObject(SqlConstant.GET_EMAIL_BY_ID+userId, String.class);
    }

    @Override
    public void saveUser(User user) {
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return jdbcTemplate.queryForList(SqlConstant.GET_ALL_USER);
    }
}
