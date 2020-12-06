package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundActionWithActionIdAndPriorityMapper;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundMapper;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundMapperWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.model.CompoundActionWithActionIdAndPriority;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class CompoundDAOImpl implements CompoundDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CompoundMapper mapper;

    @Value("${find.compound.by.id}")
    private String FIND_COMPOUND_BY_ID;
    @Value("${find.comp.action.by.id}")
    private String FIND_COMP_ACTION_BY_ID;
    @Value("${update.compound}")
    private String UPDATE_COMPOUND;
    @Value("${insert.comp.action.list}")
    private String INSERT_ACTION_TO_COMPOUND;

    @Value("${find.compound.all}")
    private String FIND_ALL;

    @Value("${find.compound.all.with.id.name}")
    private String FIND_ALL_WITH_ID_NAME;

    @Value("${get.compound.action.with.action.id.and.priority}")
    private String FIND_COMPOUND_ACTION_WITH_ACTION_ID_AND_PRIORITY_BY_COMPOUND_ID;

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
        return jdbcTemplate.query(
                FIND_ALL,
                mapper,
                pageable.getSortField(),
                pageable.getPageSize(),
                pageable.getPage()
        );
    }

    @Override
    public List<CompoundDtoWithIdName> findAllWithIdName() {
        RowMapper<CompoundDtoWithIdName> mapper = new CompoundMapperWithIdName();
        return jdbcTemplate.query(FIND_ALL_WITH_ID_NAME, mapper);
    }

    @Override
    public List<CompoundActionWithActionIdAndPriority> findAllCompoundActionsWithActionIdAndPriorityByCompoundId(long compoundId) {
        RowMapper<CompoundActionWithActionIdAndPriority> mapper =
                new CompoundActionWithActionIdAndPriorityMapper();
        return jdbcTemplate.query(
                FIND_COMPOUND_ACTION_WITH_ACTION_ID_AND_PRIORITY_BY_COMPOUND_ID,
                mapper,
                compoundId
        );
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
    @Override
    public Optional<Compound> findCompoundById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_COMPOUND_BY_ID, mapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    @Override
    public void updateCompound(Compound compound) {
        jdbcTemplate.update(UPDATE_COMPOUND, compound.getName(), compound.getDescription(), compound.getId());
    }

}
