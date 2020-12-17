package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestScenarioWithIdNameArchivedMapper implements RowMapper<TestScenario> {

    @Override
    public TestScenario mapRow(ResultSet rs, int i) throws SQLException {
        return new TestScenario(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getBoolean("is_archived")
        );
    }
}
