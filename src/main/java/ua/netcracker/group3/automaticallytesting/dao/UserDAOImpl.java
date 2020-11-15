package ua.netcracker.group3.automaticallytesting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String getEmail(Long userId) {
        return jdbcTemplate.queryForObject("SELECT email FROM \"user\" WHERE user_id ="+userId, String.class);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return jdbcTemplate.queryForList("SELECT * FROM \"user\"");
    }
}
