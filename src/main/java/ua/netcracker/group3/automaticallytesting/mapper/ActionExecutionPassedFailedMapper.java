package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.group3.automaticallytesting.model.ActionExecutionPassedFailed;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActionExecutionPassedFailedMapper implements RowMapper<ActionExecutionPassedFailed> {
    @Override
    public ActionExecutionPassedFailed mapRow(ResultSet rs, int i) throws SQLException {
        return new ActionExecutionPassedFailed (
                rs.getLong(1),
                rs.getString(2)
        );
    }
}
