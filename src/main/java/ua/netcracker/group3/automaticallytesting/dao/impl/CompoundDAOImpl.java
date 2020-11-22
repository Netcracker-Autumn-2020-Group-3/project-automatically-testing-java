package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundActionListMapper;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundMapper;
import ua.netcracker.group3.automaticallytesting.model.Compound;

import java.util.Optional;
@PropertySource("classpath:queries/postgres.properties")
@Repository
public class CompoundDAOImpl implements CompoundDAO {
    @Value("${find.compound.by.id}")
    private String FIND_COMPOUND_BY_ID;
    @Value("${find.comp.action.by.id}")
    private String FIND_COMP_ACTION_BY_ID;
    @Value("${update.compound}")
    private String UPDATE_COMPOUND;
    @Value("${insert.comp.action.list}")
    private String INSERT_ACTION_BY_ID;

    private JdbcTemplate jdbcTemplate;
    private CompoundMapper compoundMapper;
    private CompoundActionListMapper compoundActionListMapper;
    @Autowired
    public CompoundDAOImpl(JdbcTemplate jdbcTemplate, CompoundMapper mapper, CompoundActionListMapper compoundActionListMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.compoundMapper = mapper;
        this.compoundActionListMapper = compoundActionListMapper;
    }

    @Override
    public Optional<Compound> findCompActionListById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_COMP_ACTION_BY_ID, compoundActionListMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }



    @Override
    public Optional<Compound> findCompoundById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_COMPOUND_BY_ID, compoundMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateCompound(Compound compound) {
        jdbcTemplate.update(UPDATE_COMPOUND, compound.getName(), compound.getDescription(), compound.getCompound_id());
    }

//    @Override
//    public void insertActionById(long id) {
//        jdbcTemplate.update(INSERT_ACTION_BY_ID,id);
//    }
}
