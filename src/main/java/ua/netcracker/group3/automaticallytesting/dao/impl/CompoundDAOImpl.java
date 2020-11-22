package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundMapper;
import ua.netcracker.group3.automaticallytesting.model.Compound;

import java.util.List;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class CompoundDAOImpl implements CompoundDAO {

    private JdbcTemplate jdbcTemplate;
    private CompoundMapper mapper;

    @Value("${find.compound.all}")
    private String FIND_ALL;

    public CompoundDAOImpl(JdbcTemplate jdbcTemplate, CompoundMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<Compound> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }
}
