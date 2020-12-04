package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;
import ua.netcracker.group3.automaticallytesting.mapper.TestScenarioMapper;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class TestScenarioDAOImpl implements TestScenarioDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TestScenarioMapper testScenarioMapper;

    @Value("${insert.test.scenario}")
    private String INSERT_TEST_SCENARIO;

    @Value("${update.test.scenario.by.id}")
    private String UPDATE_TEST_SCENARIO_BY_ID;

    @Value("${get.test.scenarios}")
    private String GET_ALL;

    public TestScenarioDAOImpl(JdbcTemplate jdbcTemplate, TestScenarioMapper testScenarioMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.testScenarioMapper = testScenarioMapper;
    }

    @Override
    public void updateTestScenarioById(TestScenario testScenario) {
        String sql = String.format(UPDATE_TEST_SCENARIO_BY_ID, testScenario.getName(), testScenario.getId());
        jdbcTemplate.update(sql);
    }

    @Override
    public long saveTestScenario(TestScenarioDto testScenarioDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_TEST_SCENARIO, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, testScenarioDto.getName());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<TestScenario> getAll() {
        return jdbcTemplate.queryForStream(GET_ALL, testScenarioMapper).collect(Collectors.toList());
    }

}
