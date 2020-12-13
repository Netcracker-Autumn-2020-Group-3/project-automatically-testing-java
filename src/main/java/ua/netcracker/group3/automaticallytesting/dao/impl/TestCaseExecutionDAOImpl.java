package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.mapper.GroupedTestCaseExecutionMapper;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseExecutionMapper;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseExecutionWithActionFailedMapper;
import ua.netcracker.group3.automaticallytesting.mapper.TestCaseExecutionsCountsByStartDatesMapper;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecutionStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class TestCaseExecutionDAOImpl implements TestCaseExecutionDAO {

    @Value("${get.all.test.case.execution.with.failed.action.number}")
    public String GET_ALL_TEST_CASE_WITH_FAILED_ACTION;

    @Value("${select.test.case.executions.group.by.creation.date}")
    public String GET_EXECUTIONS_GROUP_BY_START_DATE;

    @Value("${dashboard.grouped.number.of.test.case.execution}")
    private String GET_GROUPED_TEST_CASE_EXECUTIONS;

    private final JdbcTemplate jdbcTemplate;
    private final TestCaseExecutionMapper testCaseExecutionMapper;
    private final TestCaseExecutionWithActionFailedMapper testCaseExecutionWithActionFailedMapper;
    private final TestCaseExecutionsCountsByStartDatesMapper testCaseExecutionsCountsByStartDatesMapper;
    private final GroupedTestCaseExecutionMapper groupedTestCaseExecutionMapper;

    public TestCaseExecutionDAOImpl(JdbcTemplate jdbcTemplate,
                                    TestCaseExecutionMapper testCaseExecutionMapper,
                                    TestCaseExecutionWithActionFailedMapper testCaseExecutionWithActionFailedMapper,
                                    TestCaseExecutionsCountsByStartDatesMapper testCaseExecutionsCountsByStartDatesMapper,
                                    GroupedTestCaseExecutionMapper groupedTestCaseExecutionMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.testCaseExecutionMapper = testCaseExecutionMapper;
        this.testCaseExecutionWithActionFailedMapper = testCaseExecutionWithActionFailedMapper;
        this.testCaseExecutionsCountsByStartDatesMapper = testCaseExecutionsCountsByStartDatesMapper;
        this.groupedTestCaseExecutionMapper = groupedTestCaseExecutionMapper;
    }

    @Override
    public List<TestCaseExecution> getAllTestCaseExecutions() {
        String sql = "select id,test_case_id,status, to_char(start_date_time, 'dd.MM.yyyy HH24:MI:SS'), to_char(end_date_time, 'dd.MM.yyyy HH24:MI:SS'), user_id from test_case_execution";
        return jdbcTemplate.query(sql, testCaseExecutionMapper);
    }

    @Override
    public List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber() {
        return jdbcTemplate.query(GET_ALL_TEST_CASE_WITH_FAILED_ACTION, testCaseExecutionWithActionFailedMapper);
    }

    @Override
    public Long createTestCaseExecution(long testCaseId, long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into test_case_execution (test_case_id, status, start_date_time,"+
                "end_date_time, user_id) values (?, ?, now(), null, ?) returning id";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1,testCaseId);
            ps.setString(2, String.valueOf(TestCaseExecutionStatus.IN_PROGRESS));
            ps.setLong(3,userId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void updateTestCaseExecution(Enum status, long testCaseExecutionId) {
        String sql = "update test_case_execution set status = ?, end_date_time = now() where id = ?";
        jdbcTemplate.update(sql, String.valueOf(status), testCaseExecutionId);
    }

    @Override
    public List<TestCaseExecutionsCountsByStartDatesDto> getExecutionsByStartDate(Date fromDate, Date tillDate){
        return jdbcTemplate.queryForStream(GET_EXECUTIONS_GROUP_BY_START_DATE, testCaseExecutionsCountsByStartDatesMapper, fromDate, tillDate)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecution() {
        return jdbcTemplate.queryForStream(GET_GROUPED_TEST_CASE_EXECUTIONS,groupedTestCaseExecutionMapper).collect(Collectors.toList());
    }
}
