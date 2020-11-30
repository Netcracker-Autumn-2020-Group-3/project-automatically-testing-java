package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.VariableValue;

import java.util.List;

public interface VariableValueDAO {

    void insert(List<VariableValue> variableValues, Long testCaseId);
}
