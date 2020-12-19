package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecutionPassedFailed;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface ActionExecutionService {
    List<ActionExecutionDto> getAllActionExecutions(Long testCaseExecutionId, Pageable pageable);
    List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(String status);

    Integer getQuantityActionsExecutions(Long testCaseExecutionId,String searchName);
}
