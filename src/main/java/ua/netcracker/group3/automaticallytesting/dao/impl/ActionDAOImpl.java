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
import ua.netcracker.group3.automaticallytesting.dto.ActionVariableDto;
import ua.netcracker.group3.automaticallytesting.mapper.ActionMapper;
import ua.netcracker.group3.automaticallytesting.mapper.ActionVariableMapper;
import ua.netcracker.group3.automaticallytesting.mapper.ActionWithIdNameVoidMapper;
import ua.netcracker.group3.automaticallytesting.model.Action;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ActionDAOImpl implements ActionDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ActionMapper actionMapper;
    private final ActionVariableMapper actionVariableMapper;

    @Value("${get.all.actions}")
    private String GET_ALL_ACTIONS;

    @Value("${get.action.variable.by.action.id}")
    private String GET_ACTION_VARIABLE_BY_ID;

    @Value("${find.actions.by.name}")
    private String FIND_ACTIONS_BY_NAME;

    @Value("${get.number.of.actions}")
    private String GET_NUMBER_OF_ACTIONS;

    @Value("${find.action.all.with.id.name}")
    private String FIND_ALL_WITH_ID_NAME;

    @Value("${update.action.description}")
    private String UPDATE_ACTION_DESCRIPTION;

    @Autowired
    public ActionDAOImpl(JdbcTemplate jdbcTemplate,ActionMapper actionMapper, ActionVariableMapper actionVariableMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.actionMapper = actionMapper;
        this.actionVariableMapper = actionVariableMapper;
    }


    /**
     * Returns list of Actions
     * @param pageActionSql needed for pagination, string that contains parameters
     * @return list of Actions
     */
    @Override
    public List<Action> getPageActions(String pageActionSql) {
        return jdbcTemplate.query(GET_ALL_ACTIONS + pageActionSql,actionMapper);
    }

    /**
     * Returns list of Actions
     * @param pageActionSql  needed for pagination, string that contains parameters
     * @param name value for searching
     * @return list of Actions
     */
    @Override
    public List<Action> findActionsByName(String pageActionSql,String name) {
        return jdbcTemplate.queryForStream(FIND_ACTIONS_BY_NAME + pageActionSql,actionMapper,name).collect(Collectors.toList());
    }

    @Override
    public List<ActionDtoWithIdNameVoid> findAllWithIdName() {
        RowMapper<ActionDtoWithIdNameVoid> mapper = new ActionWithIdNameVoidMapper();
        return jdbcTemplate.query(FIND_ALL_WITH_ID_NAME, mapper);
    }

    /**
     * @return Integer number of actions
     */
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

    /**
     * @return list of all actions
     */
    @Override
    public List<Action> getAllActions() {
        return jdbcTemplate.query(GET_ALL_ACTIONS,actionMapper);
    }

    /**
     * Return list of actions and their variables
     * @param id needed for getting value from DB by id
     * @return list of ActionVariableDto
     */
    @Override
    public List<ActionVariableDto> getActionVariable(Long id) {
        return jdbcTemplate.queryForStream(GET_ACTION_VARIABLE_BY_ID,actionVariableMapper,id).collect(Collectors.toList());
    }

    /**
     * Void method that updates action description
     * @param id needed for updating value by id
     * @param action contains updated description
     */
    @Override
    public void updateActionDescription(Long id, Action action) {
        jdbcTemplate.update(UPDATE_ACTION_DESCRIPTION,action.getActionDescription(),id);
    }
}
