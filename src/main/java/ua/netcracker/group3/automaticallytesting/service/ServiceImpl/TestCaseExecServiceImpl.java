package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;

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
    public List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber() {
        return testCaseExecutionDAO.getAllTestCaseExecutionWithFailedActionNumber();
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
    public List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecution() {
        return testCaseExecutionDAO.getGroupedTestCaseExecution();
    }
}
