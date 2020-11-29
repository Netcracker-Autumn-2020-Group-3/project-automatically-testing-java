package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.*;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActionInstanceJoinedMapper implements RowMapper<ActionInstanceJoined> {

    @Override
    public ActionInstanceJoined mapRow(ResultSet resultSet, int i) throws SQLException {
        ActionInstanceJoined ai = ActionInstanceJoined.builder()

                .id(resultSet.getLong("id"))
                .testScenarioId(resultSet.getLong("test_scenario_id"))
                .priority(resultSet.getInt("priority"))

                .action(Action.builder().actionId(resultSet.getLong("action_id"))
                        .actionName(resultSet.getString("action_name"))
                        .actionDescription(resultSet.getString("action_description")).build())

                .variable(Variable.builder()
                        .id(resultSet.getLong("variable_id"))
                        .name(resultSet.getString("variable_name")).build())

                .build();
        // needs refactoring and checking if exception can be thrown
        try {
            ai.setCompoundInstance(CompoundInstance.builder()
                    .id(resultSet.getLong("compound_instance_id"))
                    .compound(Compound.builder().id(resultSet.getLong("compound_id"))
                            .name(resultSet.getString("compound_name"))
                            .description(resultSet.getString("compound_description")).build())
                    .priority(resultSet.getInt("compound_priority")).build());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ai;
    }
}
