package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@Component
public class TestCaseExecutionMapper implements RowMapper<TestCaseExecution> {
    @Override
    public TestCaseExecution mapRow(ResultSet rs, int i) throws SQLException {
        return new TestCaseExecution(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                //rs.getTimestamp(4),
                rs.getString(4),
                //rs.getTimestamp(5),
                rs.getString(5),
                rs.getLong(6)
        );
    }
}
