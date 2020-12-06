package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.VariableValueDAO;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.VariableValue;

import java.util.List;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class VariableValueDAOImpl implements VariableValueDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public VariableValueDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Value("${insert.variable.value}")
    public String INSERT;

    @Override
    public void insert(List<VariableValue> variableValues, Long testCaseId) {
        jdbcTemplate.batchUpdate(INSERT, variableValues, variableValues.size(), (ps, variableValue) -> {
            ps.setLong(1, variableValue.getVariableId());
            ps.setLong(2, variableValue.getActionInstanceId());
            ps.setLong(3, variableValue.getDataEntryId());
            ps.setLong(4, testCaseId);
        });
    }
}
