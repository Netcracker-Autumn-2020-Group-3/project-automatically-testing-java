package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ActionDAO;
import ua.netcracker.group3.automaticallytesting.mapper.ActionMapper;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

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

    @Value("${get.number.of.actions}")
    private String GET_NUMBER_OF_ACTIONS;

    @Autowired
    public ActionDAOImpl(JdbcTemplate jdbcTemplate,ActionMapper actionMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.actionMapper = actionMapper;
    }


    @Override
    public List<Action> getPageActions(String pageActionSql) {

        return jdbcTemplate.query(GET_ALL_ACTIONS + pageActionSql,actionMapper);
    }

    @Override
    public List<Action> findActionsByName(String pageActionSql,String name) {
        return jdbcTemplate.queryForStream(FIND_ACTIONS_BY_NAME + pageActionSql,actionMapper,name).collect(Collectors.toList());
    }

    @Override
    public Integer getNumberOfActions() {
        return jdbcTemplate.queryForObject(GET_NUMBER_OF_ACTIONS,Integer.class);
    }


}
