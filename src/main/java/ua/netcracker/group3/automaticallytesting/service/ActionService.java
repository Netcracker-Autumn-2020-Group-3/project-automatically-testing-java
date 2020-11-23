package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.Action;

import java.util.List;

public interface ActionService {

    public List<Action> getAllActions();
    public List<Action> findActionsByName(String name);
}
