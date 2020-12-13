package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.TestCaseTopSubscribed;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestCaseTopSubscribedMapper implements RowMapper<TestCaseTopSubscribed> {

    @Override
    public TestCaseTopSubscribed mapRow(ResultSet rs, int i) throws SQLException {
        return new TestCaseTopSubscribed(
                rs.getLong("test_case_id"),
                rs.getString("test_case_name"),
                rs.getLong("project_id"),
                rs.getString("project_name"),
                rs.getLong("quantity")
        );
    }
}
