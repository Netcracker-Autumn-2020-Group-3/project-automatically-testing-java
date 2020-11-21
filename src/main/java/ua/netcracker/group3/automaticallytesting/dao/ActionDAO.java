package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.Action;

import java.util.List;
import java.util.Map;

public interface ActionDAO {
    List<Action> getPageActions();
    List<Action> findActionsByName(String name);
}
