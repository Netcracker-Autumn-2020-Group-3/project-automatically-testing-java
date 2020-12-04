package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseDAO;
import ua.netcracker.group3.automaticallytesting.dao.TestScenarioDAO;
import ua.netcracker.group3.automaticallytesting.dao.VariableValueDAO;
import ua.netcracker.group3.automaticallytesting.dto.CreateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;
import ua.netcracker.group3.automaticallytesting.model.VariableValue;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;

import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    private TestCaseDAO testCaseDAO;
    private VariableValueDAO variableValueDAO;


    public TestCaseServiceImpl(TestCaseDAO testCaseDAO,  VariableValueDAO variableValueDAO) {
        this.testCaseDAO = testCaseDAO;
        this.variableValueDAO = variableValueDAO;
    }

    @Transactional
    public void createTestCase(CreateTestCaseDto createTestCaseDto, Long userId){

        TestCase testCase = TestCase.builder().name(createTestCaseDto.getTestCaseName())
                .testScenarioId(createTestCaseDto.getTestScenarioId())
                .datasetId(createTestCaseDto.getDataSetId())
                .projectId(createTestCaseDto.getProjectId())
                .userId(userId)
                .build();

        Long testCaseId = testCaseDAO.insert(testCase);

        variableValueDAO.insert(createTestCaseDto.getVariableValues(), testCaseId);


    }

    @Override
    public List<TestCaseUpd> getAllTestCases() {
        return testCaseDAO.getTestCases();
    }
}
