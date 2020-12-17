package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;
import ua.netcracker.group3.automaticallytesting.dto.ActionVariableDto;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface ActionService {

    List<Action> getAllActions(Pageable pageable);
    List<Action> getAllActions();
    List<Action> findActionsByName(String name,Pageable pageable);
    Integer getNumberOfActions();

    List<ActionDtoWithIdNameVoid> getAllActionsWithIdName();

    long createAction(String name, String description);

    List<ActionVariableDto> getActionVariableById(Long id);

    void updateActionDescription(Long id, Action action);
}
