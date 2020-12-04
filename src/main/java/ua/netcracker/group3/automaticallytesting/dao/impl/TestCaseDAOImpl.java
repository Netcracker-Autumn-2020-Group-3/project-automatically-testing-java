package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseDAO;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseUpdMapper;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class TestCaseDAOImpl implements TestCaseDAO {

    private JdbcTemplate jdbcTemplate;

    private TestCaseUpdMapper testCaseUpdMapper;

    public TestCaseDAOImpl(JdbcTemplate jdbcTemplate, TestCaseUpdMapper testCaseUpdMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.testCaseUpdMapper = testCaseUpdMapper;
    }

    @Value("${insert.test.case}")
    public String INSERT;
    @Value("select id, name from \"test_case\"")
    public String GET_ALL;
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
            ps.setLong(4, testCase.getDatasetId());
            ps.setLong(5, testCase.getTestScenarioId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<TestCaseUpd> getTestCases() {
        return jdbcTemplate.queryForStream(GET_ALL,testCaseUpdMapper).collect(Collectors.toList());
    }
}
