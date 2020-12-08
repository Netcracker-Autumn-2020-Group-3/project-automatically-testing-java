package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;
import java.util.Map;

public interface ActionDAO {

    List<Action> getPageActions(String pageActionSql);

    List<Action> findActionsByName(String pageActionSql,String name);

    List<ActionDtoWithIdName> findAllWithIdName();

    Integer getNumberOfActions();

    long createAction(String name, String description);

    List<Action> getAllActions();
}
