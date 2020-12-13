package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ActionExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.mapper.ActionExecutionMapper;
import ua.netcracker.group3.automaticallytesting.mapper.ActionExecutionPassedFailedMapper;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.model.ActionExecutionPassedFailed;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ActionExecutionDAOImpl implements ActionExecutionDAO {


    private final JdbcTemplate jdbcTemplate;
    private final ActionExecutionMapper actionExecutionMapper;
    private final ActionExecutionPassedFailedMapper actionExecutionPassedFailedMapper;

    @Value("${create.action.executions}")
    private String CREATE_ACTION_EXECUTIONS;

    @Value("${get.list.of.action.executions.v2}")
    private String GET_ALL_ACTION_EXECUTIONS;

    @Value("${get.number.of.failed.passed.action.executions}")
    private String GET_NUMBER_ACTION_EXECUTION;

    public ActionExecutionDAOImpl(JdbcTemplate jdbcTemplate, ActionExecutionMapper actionExecutionMapper, ActionExecutionPassedFailedMapper actionExecutionPassedFailedMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.actionExecutionMapper = actionExecutionMapper;
        this.actionExecutionPassedFailedMapper = actionExecutionPassedFailedMapper;
    }

    @Override
    public void addActionExecution(List<ActionExecution> actionExecutionList) {
        jdbcTemplate.batchUpdate(CREATE_ACTION_EXECUTIONS,actionExecutionList,actionExecutionList.size(),
                ((preparedStatement, actionExecutionValue) -> {
            preparedStatement.setLong(1,actionExecutionValue.getTestCaseExecutionId());
            preparedStatement.setLong(2,actionExecutionValue.getActionInstanceId());
            preparedStatement.setString(3,actionExecutionValue.getStatus());
        }));
    }

    @Override
    public List<ActionExecutionDto> getAllActionExecution(Long testCaseExecutionId) {
        return jdbcTemplate.queryForStream(GET_ALL_ACTION_EXECUTIONS,actionExecutionMapper,testCaseExecutionId,testCaseExecutionId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(String status) {
        return jdbcTemplate.queryForStream(GET_NUMBER_ACTION_EXECUTION, actionExecutionPassedFailedMapper, status).collect(Collectors.toList());
    }

}
