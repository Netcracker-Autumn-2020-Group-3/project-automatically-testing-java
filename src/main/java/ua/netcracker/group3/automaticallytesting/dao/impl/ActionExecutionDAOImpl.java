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

    @Value("${get.list.of.action.executions}")
    private String GET_ALL_ACTION_EXECUTIONS;

    @Value("${get.number.of.failed.passed.action.executions}")
    private String GET_NUMBER_ACTION_EXECUTION;

    @Value("${get.count.action.executions}")
    private String GET_COUNT_ACTION_EXECUTIONS;

    @Value("${action.execution.search.name.sql}")
    private String ACTION_EXECUTION_SEARCH_NAME;

    @Value("${action.execution.count.search.name.sql}")
    private String ACTION_EXECUTION_COUNT_SEARCH_NAME;


    public ActionExecutionDAOImpl(JdbcTemplate jdbcTemplate, ActionExecutionMapper actionExecutionMapper, ActionExecutionPassedFailedMapper actionExecutionPassedFailedMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.actionExecutionMapper = actionExecutionMapper;
        this.actionExecutionPassedFailedMapper = actionExecutionPassedFailedMapper;
    }

    /**
     * @param actionExecutionList needed for adding list of action execution to DB
     */
    @Override
    public void addActionExecution(List<ActionExecution> actionExecutionList) {
        jdbcTemplate.batchUpdate(CREATE_ACTION_EXECUTIONS,actionExecutionList,actionExecutionList.size(),
                ((preparedStatement, actionExecutionValue) -> {
            preparedStatement.setLong(1,actionExecutionValue.getTestCaseExecutionId());
            preparedStatement.setLong(2,actionExecutionValue.getActionInstanceId());
            preparedStatement.setString(3,actionExecutionValue.getStatus());
        }));
    }

    /**
     * Method make part of string sql and make getting values from DB
     * @param testCaseExecutionId needed for getting values from DB
     * @param pagination needed for pagination
     * @param searchName needed for searching by value
     * @return list of ActionExecutionDto
     */
    @Override
    public List<ActionExecutionDto> getAllActionExecution(Long testCaseExecutionId, String pagination,String searchName) {
        String searchNameSql = searchName == null || searchName.equals("") ? "" :
                String.format(ACTION_EXECUTION_SEARCH_NAME,searchName);
        return jdbcTemplate.queryForStream(GET_ALL_ACTION_EXECUTIONS + searchNameSql + pagination,actionExecutionMapper,testCaseExecutionId,testCaseExecutionId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(String status) {
        return jdbcTemplate.queryForStream(GET_NUMBER_ACTION_EXECUTION, actionExecutionPassedFailedMapper, status).collect(Collectors.toList());
    }

    /**
     * @param testCaseExecutionId needed for getting values from DB
     * @param searchName needed for searching by value
     * @return Integer number of actionExecutions
     */
    @Override
    public Integer getQuantityActionsExecutions(Long testCaseExecutionId,String searchName) {
        String searchNameSql = searchName == null || searchName.equals("") ? "" :
                String.format(ACTION_EXECUTION_COUNT_SEARCH_NAME,searchName);
        return jdbcTemplate.queryForObject(GET_COUNT_ACTION_EXECUTIONS + searchNameSql,Integer.class,testCaseExecutionId);
    }

}
