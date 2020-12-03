package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundMapper;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class CompoundDAOImpl implements CompoundDAO {

    private JdbcTemplate jdbcTemplate;
    private CompoundMapper mapper;

    @Value("${find.compound.all}")
    private String FIND_ALL;

    @Value("${check.if.compound.name.exist}")
    private String CHECK_IF_COMPOUND_NAME_EXIST;

    @Value("${insert.compound}")
    private String CREATE_COMPOUND;

    @Value("${insert.compound.actions}")
    private String CREATE_COMPOUND_ACTIONS;

    public CompoundDAOImpl(JdbcTemplate jdbcTemplate, CompoundMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<Compound> findAll(Pageable pageable) {
        String sql = String.format(FIND_ALL,
                pageable.getSortField(),
                pageable.getSortOrder(),
                pageable.getPageSize(),
                pageable.getPage()
        );
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public boolean checkIfNameExist(String name) {
        return jdbcTemplate.queryForObject(CHECK_IF_COMPOUND_NAME_EXIST,Boolean.class,name);
    }

    @Override
    public Integer createCompound(Compound compound) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(CREATE_COMPOUND, new String[] {"id"});
            ps.setString(1,compound.getName());
            ps.setString(2,compound.getDescription());
            return ps;
        },keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void createCompoundActions(List<CompoundAction> compoundActions) {
        jdbcTemplate.batchUpdate(CREATE_COMPOUND_ACTIONS,compoundActions,compoundActions.size(),((ps, compoundActionsValue) -> {
            ps.setLong(1,compoundActionsValue.getCompoundId());
            ps.setLong(2,compoundActionsValue.getActionId());
            ps.setInt(3,compoundActionsValue.getPriority());
        }));
    }

}
