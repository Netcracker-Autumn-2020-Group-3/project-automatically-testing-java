package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.Action;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActionMapper implements RowMapper<Action> {


    @Override
    public Action mapRow(ResultSet resultSet, int i) throws SQLException {
        return Action.builder().
                actionId(resultSet.getLong("action_id"))
                .actionName(resultSet.getString("name"))
                .actionDescription(resultSet.getString("description"))
                .build();
    }
}
