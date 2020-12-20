package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestScenarioMapper implements RowMapper<TestScenario> {

    @Override
    public TestScenario mapRow(ResultSet rs, int i) throws SQLException {
        return new TestScenario(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
