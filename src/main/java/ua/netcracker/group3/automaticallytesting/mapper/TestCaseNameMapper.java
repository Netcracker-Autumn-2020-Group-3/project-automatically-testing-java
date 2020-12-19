package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestCaseNameMapper implements RowMapper<TestCase> {
    @Override
    public TestCase mapRow(ResultSet resultSet, int i) throws SQLException {
        return TestCase.builder()
                .name(resultSet.getString(1))
                .build();
    }
}
