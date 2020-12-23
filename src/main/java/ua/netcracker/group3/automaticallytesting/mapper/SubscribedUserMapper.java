package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscribedUserMapper implements RowMapper<SubscribedUserTestCaseDto> {

    /**
     * @param resultSet contains result from DB
     * @param i integer
     * @return SubscribedUserTestCaseDto
     * @throws SQLException throw SQLException
     */
    @Override
    public SubscribedUserTestCaseDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return SubscribedUserTestCaseDto.builder()
                .id(resultSet.getLong(1))
                .userName(resultSet.getString(2))
                .email(resultSet.getString(3))
                .testCaseId(resultSet.getLong(4))
                .testCaseName(resultSet.getString(5))
                .build();
    }
}
