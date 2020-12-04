package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseDAO;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseStepMapper;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseStep;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class TestCaseDAOImpl implements TestCaseDAO {

    private JdbcTemplate jdbcTemplate;
    private TestCaseStepMapper testCaseStepMapper;

    public TestCaseDAOImpl(JdbcTemplate jdbcTemplate, TestCaseStepMapper testCaseStepMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.testCaseStepMapper = testCaseStepMapper;
    }

    @Value("${insert.test.case}")
    public String INSERT;

    @Value("${get.test.case.steps}")
    private String GET_TEST_CASE_STEPS;

    /**
     *
     * @return created test_case_id
     */
    @Override
    public long insert(TestCase testCase) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT, new String[]{"id"});
            ps.setString(1, testCase.getName());
            ps.setLong(2, testCase.getUserId());
            ps.setLong(3, testCase.getProjectId());
            ps.setLong(4, testCase.getDataSetId());
            ps.setLong(5, testCase.getTestScenarioId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<TestCaseStep> getTestCaseSteps(Long testCaseId){
        return jdbcTemplate.queryForStream(GET_TEST_CASE_STEPS, testCaseStepMapper, testCaseId).collect(Collectors.toList());
    }
}
