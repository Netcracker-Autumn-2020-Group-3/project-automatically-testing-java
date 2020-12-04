package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.CompoundInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioItemDto;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class CompoundInstanceDAOImpl implements CompoundInstanceDAO {

    private final JdbcTemplate jdbcTemplate;

    @Value("${insert.compound.instance}")
    private String INSERT_AND_GET_GENERATED_ID;

    public CompoundInstanceDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long saveCompoundInstanceAndGetGeneratedId(TestScenarioItemDto dto, long testScenarioId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_AND_GET_GENERATED_ID, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getId());
            ps.setLong(2, testScenarioId);
            ps.setLong(3, dto.getPriority());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
