package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;

import java.util.List;

public interface ActionExecutionService {
    List<ActionExecutionDto> getAllActionExecutions(Long testCaseExecutionId);
}
