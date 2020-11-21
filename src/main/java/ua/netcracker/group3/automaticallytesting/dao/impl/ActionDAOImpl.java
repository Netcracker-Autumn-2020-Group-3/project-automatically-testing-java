package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ActionDAO;
import ua.netcracker.group3.automaticallytesting.mapper.ActionMapper;
import ua.netcracker.group3.automaticallytesting.model.Action;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ActionDAOImpl implements ActionDAO {

    private final JdbcTemplate jdbcTemplate;
    private ActionMapper actionMapper;

    @Value("${get.all.actions}")
    private String GET_ALL_ACTIONS;

    @Value("${find.actions.by.name}")
    private String FIND_ACTIONS_BY_NAME;

    @Autowired
    public ActionDAOImpl(JdbcTemplate jdbcTemplate,ActionMapper actionMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.actionMapper = actionMapper;
    }


    @Override
    public List<Action> getPageActions() {

        return jdbcTemplate.query(GET_ALL_ACTIONS,actionMapper);
    }

    @Override
    public List<Action> findActionsByName(String name) {

        return jdbcTemplate.queryForStream(FIND_ACTIONS_BY_NAME,actionMapper,name).collect(Collectors.toList());
    }


}
