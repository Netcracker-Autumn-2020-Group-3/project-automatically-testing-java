package ua.netcracker.group3.automaticallytesting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MyDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> method() {
        return jdbcTemplate.queryForList("SELECT * FROM \"user\"");
    }

}
