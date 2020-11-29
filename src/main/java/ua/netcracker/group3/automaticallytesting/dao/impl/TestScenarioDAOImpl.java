package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.mapper.TestScenarioMapper;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class TestScenarioDAOImpl implements TestScenarioDAO {

    private JdbcTemplate jdbcTemplate;
    private TestScenarioMapper testScenarioMapper;

    @Value("${insert.test.scenario}")
    private String INSERT_TEST_SCENARIO;

    @Value("${update.test.scenario.by.id}")
    private String UPDATE_TEST_SCENARIO_BY_ID;

    @Value("${get.test.scenarios}")
    private String GET_ALL;

    @Autowired
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
    public void saveTestScenario(TestScenario testScenario) {
        String sql = String.format(INSERT_TEST_SCENARIO, testScenario.getName());
        jdbcTemplate.update(sql);
    }

    @Override
    public List<TestScenario> getAll() {
        return jdbcTemplate.queryForStream(GET_ALL, testScenarioMapper).collect(Collectors.toList());
    }
}
