package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.model.ActionExecutionPassedFailed;

import java.util.List;

public interface ActionExecutionDAO {
    void addActionExecution(List<ActionExecution> actionExecutionList);
    List<ActionExecutionDto> getAllActionExecution(Long testCaseExecutionId);
    List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(String status);

}
