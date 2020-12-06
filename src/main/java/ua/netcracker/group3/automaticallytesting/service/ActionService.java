package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface ActionService {

    List<Action> getAllActions(Pageable pageable);
    List<Action> findActionsByName(String name,Pageable pageable);
    Integer getNumberOfActions();

    List<ActionDtoWithIdName> getAllActionsWithIdName();

    long createAction(String name, String description);
}
