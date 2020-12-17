package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseDAO;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseStepMapper;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseTopSubscribedMapper;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseUpdMapper;
import ua.netcracker.group3.automaticallytesting.model.*;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class TestCaseDAOImpl implements TestCaseDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TestCaseStepMapper testCaseStepMapper;
    private final TestCaseTopSubscribedMapper testCaseTopSubscribedMapper;
    private final TestCaseUpdMapper testCaseUpdMapper;

    public TestCaseDAOImpl(JdbcTemplate jdbcTemplate, TestCaseStepMapper testCaseStepMapper, TestCaseTopSubscribedMapper testCaseTopSubscribedMapper, TestCaseUpdMapper testCaseUpdMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.testCaseStepMapper = testCaseStepMapper;
        this.testCaseTopSubscribedMapper = testCaseTopSubscribedMapper;
        this.testCaseUpdMapper = testCaseUpdMapper;
    }

    @Value("${insert.test.case}")
    public String INSERT;

    @Value("${get.test.case.steps}")
    private String GET_TEST_CASE_STEPS;

    @Value("select id, name from \"test_case\"")
    public String GET_ALL;

    @Value("${get.test.case.page}")
    public String GET_PAGE;

    @Value("${count.test.cases}")
    public String COUNT_TEST_CASES;

    @Value("${update.test.case.name}")
    public String UPDATE_TEST_CASE_NAME;

    @Value("${insert.subscription}")
    public String INSERT_SUBSCRIBER;

    @Value("${delete.subscription}")
    public String DELETE_SUBSCRIBER;

    @Value("${exist.subscription}")
    public String SUBSCRIPTION_EXISTS;

    @Value("${dashboard.top.subscribed.test.cases}")
    public String GET_TOP_FIVE_SUBSCRIBED_TEST_CASES;

    /**
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

    public List<TestCaseStep> getTestCaseSteps(Long testCaseId) {
        return jdbcTemplate.queryForStream(GET_TEST_CASE_STEPS, testCaseStepMapper, testCaseId).collect(Collectors.toList());
    }

    @Override
    public List<TestCaseUpd> getTestCases() {
        return jdbcTemplate.queryForStream(GET_ALL, testCaseUpdMapper).collect(Collectors.toList());
    }

    @Override
    public List<TestCaseUpd> getTestCasesPageSorted(String orderByLimitOffsetWithValues, String name) {
        return jdbcTemplate.queryForStream(GET_PAGE + orderByLimitOffsetWithValues,
                testCaseUpdMapper, name)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCaseTopSubscribed> getTopFiveSubscribedTestCases() {
        return jdbcTemplate.query(GET_TOP_FIVE_SUBSCRIBED_TEST_CASES,
                testCaseTopSubscribedMapper);
    }

    @Override
    public Integer countUsers() {
        return jdbcTemplate.queryForObject(COUNT_TEST_CASES, Integer.class);
    }

    @Override
    public void update(Long testCaseId, String newTestCaseName) {
        jdbcTemplate.update(UPDATE_TEST_CASE_NAME, newTestCaseName, testCaseId);
    }

    @Override
    public void addSubscriber(Long testCaseId, Long userId) {
        jdbcTemplate.update(INSERT_SUBSCRIBER, userId, testCaseId);
    }

    @Override
    public Boolean isFollowedByUser(Long testCaseId, Long userId) {
        return jdbcTemplate.queryForObject(SUBSCRIPTION_EXISTS, Boolean.class, testCaseId, userId);
    }

    @Override
    public void removeSubscriber(Long testCaseId, Long userId) {
        jdbcTemplate.update(DELETE_SUBSCRIBER, testCaseId, userId);
    }
}
