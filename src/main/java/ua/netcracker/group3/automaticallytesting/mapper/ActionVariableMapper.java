package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.ActionVariableDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActionVariableMapper implements RowMapper<ActionVariableDto> {
    @Override
    public ActionVariableDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return ActionVariableDto.builder()
                .actionId(resultSet.getLong(1))
                .actionName(resultSet.getString(2))
                .actionDescription(resultSet.getString(3))
                .variableName(resultSet.getString(4))
                .build();
    }
}
