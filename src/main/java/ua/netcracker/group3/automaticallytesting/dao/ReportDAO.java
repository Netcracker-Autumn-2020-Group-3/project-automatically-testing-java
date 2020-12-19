package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;

import java.util.List;

public interface ReportDAO {
    List<SubscribedUserTestCaseDto> getSubscribedUsers(Long testCaseExecutionId);
}
