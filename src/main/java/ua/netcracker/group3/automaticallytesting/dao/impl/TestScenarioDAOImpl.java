package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class TestScenarioDAOImpl implements TestScenarioDAO {

    private JdbcTemplate jdbcTemplate;

    @Value("${insert.test.scenario}")
    private String INSERT_TEST_SCENARIO;

    @Value("${update.test.scenario.by.id}")
    private String UPDATE_TEST_SCENARIO_BY_ID;

    public TestScenarioDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
