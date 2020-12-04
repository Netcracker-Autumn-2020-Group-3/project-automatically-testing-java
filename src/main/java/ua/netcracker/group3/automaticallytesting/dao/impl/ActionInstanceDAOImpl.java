package ua.netcracker.group3.automaticallytesting.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ActionInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioItemDto;
import ua.netcracker.group3.automaticallytesting.mapper.ActionInstanceJoinedMapper;
import ua.netcracker.group3.automaticallytesting.mapper.DataEntryMapper;
import ua.netcracker.group3.automaticallytesting.model.ActionInstanceJoined;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class ActionInstanceDAOImpl implements ActionInstanceDAO {

    JdbcTemplate jdbcTemplate;
    ActionInstanceJoinedMapper actionInstanceJoinedMapper;

    @Value("${get.action.instance.by.test.case}")
    private String GET_ACTION_INSTANCE_JOINED_BY_TEST_CASE_ID;

    @Value("${insert.action.instance.with.compound.instance.id}")
    private String INSERT_ALL_WITH_COMPOUND_INSTANCE_ID;

    @Value("${insert.action.instance.without.compound.instance.id}")
    private String INSERT_ALL_WITHOUT_COMPOUND_INSTANCE_ID;

    @Autowired
    public ActionInstanceDAOImpl(JdbcTemplate jdbcTemplate, ActionInstanceJoinedMapper actionInstanceJoinedMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.actionInstanceJoinedMapper = actionInstanceJoinedMapper;
    }

    @Override
    public List<ActionInstanceJoined> getActionInstanceJoinedByTestCaseId(Long testCaseId) {
        return jdbcTemplate.queryForStream(GET_ACTION_INSTANCE_JOINED_BY_TEST_CASE_ID, actionInstanceJoinedMapper, testCaseId).collect(Collectors.toList());
    }

    @Override
    public void saveActionInstancesWithoutCompoundInstanceId(List<TestScenarioItemDto> actions, long testScenarioId) {
        jdbcTemplate.batchUpdate(
                INSERT_ALL_WITHOUT_COMPOUND_INSTANCE_ID,
                actions,
                actions.size(),
                (preparedStatement, action) -> {
                    preparedStatement.setLong(1, testScenarioId);
                    preparedStatement.setLong(2, action.getId());
                    preparedStatement.setLong(3, action.getPriority());
        });
    }

    @Override
    public void saveActionInstancesWithCompoundInstanceId(List<TestScenarioItemDto> actions, long testScenarioId, long compoundInstanceId) {
        jdbcTemplate.batchUpdate(
                INSERT_ALL_WITH_COMPOUND_INSTANCE_ID,
                actions,
                actions.size(),
                (preparedStatement, action) -> {
                    preparedStatement.setLong(1, testScenarioId);
                    preparedStatement.setLong(2, compoundInstanceId);
                    preparedStatement.setLong(3, action.getId());
                    preparedStatement.setLong(4, action.getPriority());
                });
    }
}
