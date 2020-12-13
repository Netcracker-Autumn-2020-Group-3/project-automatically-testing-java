package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ActionDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;
import ua.netcracker.group3.automaticallytesting.mapper.ActionMapper;
import ua.netcracker.group3.automaticallytesting.mapper.ActionWithIdNameVoidMapper;
import ua.netcracker.group3.automaticallytesting.model.Action;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
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

    @Value("${find.action.all.with.id.name}")
    private String FIND_ALL_WITH_ID_NAME;

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
    public List<ActionDtoWithIdNameVoid> findAllWithIdName() {
        RowMapper<ActionDtoWithIdNameVoid> mapper = new ActionWithIdNameVoidMapper();
        return jdbcTemplate.query(FIND_ALL_WITH_ID_NAME, mapper);
    }

    @Override
    public Integer getNumberOfActions() {
        return jdbcTemplate.queryForObject(GET_NUMBER_OF_ACTIONS,Integer.class);
    }

    @Override
    public long createAction(String name, String description) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into action (name, description, is_void) values (?, ?, true) returning id";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Action> getAllActions() {
        return jdbcTemplate.query(GET_ALL_ACTIONS,actionMapper);
    }
}
