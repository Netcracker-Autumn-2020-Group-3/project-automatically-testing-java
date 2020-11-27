package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.mapper.CompoundMapper;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
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
    public List<Compound> findAll(Pageable pageable) {
        String sql = String.format(FIND_ALL,
                pageable.getSortField(),
                pageable.getSortOrder(),
                pageable.getPageSize(),
                pageable.getPage()
        );
        return jdbcTemplate.query(sql, mapper);
    }
}
