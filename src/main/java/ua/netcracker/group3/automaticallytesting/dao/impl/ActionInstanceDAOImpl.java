package ua.netcracker.group3.automaticallytesting.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ActionInstanceDAO;
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

    @Autowired
    public ActionInstanceDAOImpl(JdbcTemplate jdbcTemplate, ActionInstanceJoinedMapper actionInstanceJoinedMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.actionInstanceJoinedMapper = actionInstanceJoinedMapper;
    }

    @Override
    public List<ActionInstanceJoined> getActionInstanceJoinedByTestCaseId(Long testCaseId) {
        return jdbcTemplate.queryForStream(GET_ACTION_INSTANCE_JOINED_BY_TEST_CASE_ID, actionInstanceJoinedMapper, testCaseId).collect(Collectors.toList());
    }
}
