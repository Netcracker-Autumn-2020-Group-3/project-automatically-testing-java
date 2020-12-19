package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestCaseExecutionStatusStartDateMapper implements RowMapper<TestCaseExecution> {
    @Override
    public TestCaseExecution mapRow(ResultSet resultSet, int i) throws SQLException {
        return TestCaseExecution.builder()
                .status(resultSet.getString(1))
                .startDateTime(resultSet.getString(2))
                .build();
    }
}
