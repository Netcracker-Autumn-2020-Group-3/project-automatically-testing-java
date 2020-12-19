package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class TestCaseExecServiceImpl implements TestCaseExecService {

    private final TestCaseExecutionDAO testCaseExecutionDAO;

    public TestCaseExecServiceImpl(TestCaseExecutionDAO testCaseExecutionDAO) {
        this.testCaseExecutionDAO = testCaseExecutionDAO;
    }

    @Override
    public List<TestCaseExecution> getAllTestCaseExecutions() {
        return testCaseExecutionDAO.getAllTestCaseExecutions();
    }

    @Override
    public Integer countTestCaseExecutions() {
        return testCaseExecutionDAO.countTestCaseExecutions();
    };

    @Override
    public List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber(long limit, long offset, String orderBy, String orderByClause, String testCaseName, String projectName, String status) {
        if (testCaseName.equals("undefined")) {
            testCaseName = "";
        }
        if (projectName.equals("undefined")) {
            projectName = "";
        }
        return testCaseExecutionDAO.getAllTestCaseExecutionWithFailedActionNumber(limit, offset, orderBy, orderByClause, testCaseName, projectName, status);
    }

    @Override
    public Long createTestCaseExecution(Long testCaseId, long userId) {
        return testCaseExecutionDAO.createTestCaseExecution(testCaseId, userId);
    }

    @Override
    public void updateTestCaseExecution(Enum status, long testCaseExecutionId) {
        testCaseExecutionDAO.updateTestCaseExecution(status, testCaseExecutionId);
    }

    @Override
    public List<TestCaseExecutionsCountsByStartDatesDto> getExecutionsByDatesForLastDays(Integer daysFromToday){
        LocalDate today = LocalDate.now();
        LocalDate dateToCountFrom = today.minusDays(daysFromToday);
        return testCaseExecutionDAO.getExecutionsByStartDate(Date.valueOf(dateToCountFrom), Date.valueOf(today));
    }

    @Override
    public List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecution() {
        return testCaseExecutionDAO.getGroupedTestCaseExecution();
    }
}
