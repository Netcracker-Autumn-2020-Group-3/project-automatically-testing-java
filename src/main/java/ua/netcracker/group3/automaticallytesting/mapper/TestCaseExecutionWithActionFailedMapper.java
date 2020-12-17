package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestCaseExecutionWithActionFailedMapper implements RowMapper<TestCaseExecutionDto> {
    @Override
    public TestCaseExecutionDto mapRow(ResultSet rs, int i) throws SQLException {
        return new TestCaseExecutionDto(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getLong(5),
                rs.getString(6),
                rs.getString(7),
                rs.getLong(8),
                rs.getLong(9)
        );
    }
}
