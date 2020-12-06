package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseStep;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestCaseStepMapper implements RowMapper<TestCaseStep> {

    private final ActionInstanceJoinedMapper actionInstanceJoinedMapper;

    public TestCaseStepMapper(ActionInstanceJoinedMapper actionInstanceJoinedMapper) {
        this.actionInstanceJoinedMapper = actionInstanceJoinedMapper;
    }

    @Override
    public TestCaseStep mapRow(ResultSet resultSet, int i) throws SQLException {
        return TestCaseStep.builder()
                .testCase(TestCase.builder()
                        .userId(resultSet.getLong("test_case_user_id"))
                        .projectId(resultSet.getLong("test_case_project_id"))
                        .dataSetId(resultSet.getLong("test_case_data_set_id"))
                        .testScenarioId(resultSet.getLong("test_case_test_scenario_id"))
                        .name(resultSet.getString("test_case_name"))
                        .id(resultSet.getLong("test_case_id"))
                        .build())
                .projectLink(resultSet.getString("test_case_project_link"))
                .projectName(resultSet.getString("test_case_project_name"))
                .actionInstanceJoined(actionInstanceJoinedMapper.mapRow(resultSet, i)).dataEntry(
                        DataEntry.builder()
                                .id(resultSet.getLong("data_entry_id"))
                                .data_set_id(resultSet.getLong("data_set_id"))
                                .value(resultSet.getString("data_entry_value"))
                                .key(resultSet.getString("data_entry_key"))
                                .build())
                .build();
    }
}
