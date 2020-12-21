package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.Variable;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActionExecutionMapper implements RowMapper<ActionExecutionDto> {

    /**
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public ActionExecutionDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return ActionExecutionDto.builder()
                .id(resultSet.getInt("action_execution_id"))
                .action(Action.builder()
                        .actionId(resultSet.getLong("action_id"))
                        .actionName(resultSet.getString("action_name"))
                        .actionDescription(resultSet.getString("action_description"))
                        .isVoid(resultSet.getBoolean("action_is_void"))
                        .build())
                .variable(Variable.builder()
                        .name(resultSet.getString("variable_name"))
                        .build())
                .dataEntry(DataEntry.builder()
                        .key(resultSet.getString("variable_key"))
                        .value(resultSet.getString("variable_value"))
                        .build())
                .testcaseId(resultSet.getLong("test_case_id"))
                .testcaseExecutionId(resultSet.getLong("test_case_execution_id"))
                .status(resultSet.getString("status"))
                .build();
    }
}
