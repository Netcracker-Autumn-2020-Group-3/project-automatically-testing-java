package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupedTestCaseExecutionMapper implements RowMapper<GroupedTestCaseExecutionDto> {
    /**
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public GroupedTestCaseExecutionDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return GroupedTestCaseExecutionDto.builder()
                .testCaseId(resultSet.getLong(1))
                .testCaseName(resultSet.getString(2))
                .numberOfTestCaseExecution(resultSet.getInt(3))
                .build();
    }
}
