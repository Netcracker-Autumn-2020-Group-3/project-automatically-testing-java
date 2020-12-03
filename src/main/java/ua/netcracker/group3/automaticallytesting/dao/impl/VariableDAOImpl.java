package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.VariableDAO;

import java.util.List;

@Repository
public class VariableDAOImpl implements VariableDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public VariableDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createVariables(Long actionId, List<String> names) {
        String sql = "insert into variable (name, action_id) values (?, ?)";
        jdbcTemplate.batchUpdate(sql, names, names.size(), (ps, name) -> {
            ps.setString(1, name);
            ps.setLong(2, actionId);
        });
    }
}
