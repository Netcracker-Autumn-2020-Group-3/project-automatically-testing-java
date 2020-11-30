package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.ActionInstanceJoined;

import java.util.List;

public interface ActionInstanceDAO {
    List<ActionInstanceJoined> getActionInstanceJoinedByTestCaseId(Long testCaseId);
}
