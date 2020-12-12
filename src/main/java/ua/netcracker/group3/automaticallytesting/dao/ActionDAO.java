package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;
import ua.netcracker.group3.automaticallytesting.model.Action;

import java.util.List;

public interface ActionDAO {

    List<Action> getPageActions(String pageActionSql);

    List<Action> findActionsByName(String pageActionSql,String name);

    List<ActionDtoWithIdNameVoid> findAllWithIdName();

    Integer getNumberOfActions();

    long createAction(String name, String description);

    List<Action> getAllActions();
}
