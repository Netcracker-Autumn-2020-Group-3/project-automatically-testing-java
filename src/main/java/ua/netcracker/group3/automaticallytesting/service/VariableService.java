package ua.netcracker.group3.automaticallytesting.service;

import java.util.List;

public interface VariableService {
    void createVariables(Long actionId, List<String> names);
}
