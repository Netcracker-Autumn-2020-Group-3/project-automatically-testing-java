package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;

import java.util.List;

public interface ReportService {
    void sendReportToUser(List<ActionExecutionDto> actionExecutionList, List<SubscribedUserTestCaseDto> subscribedUsers);
    List<SubscribedUserTestCaseDto> getSubscribedUsers(Long testCaseExecutionId);
}
