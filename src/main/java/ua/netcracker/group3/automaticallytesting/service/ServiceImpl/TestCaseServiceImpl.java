package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.ActionInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseDAO;
import ua.netcracker.group3.automaticallytesting.dao.VariableValueDAO;
import ua.netcracker.group3.automaticallytesting.dto.*;
import ua.netcracker.group3.automaticallytesting.model.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateUpdateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.util.Pagination;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseDAO testCaseDAO;
    private final VariableValueDAO variableValueDAO;
    private final ActionInstanceDAO actionInstanceDAO;
    private final Pagination pagination;

    private final List<String> TEST_CASE_UPD_TABLE_FIELDS = Arrays.asList("id", "name");
    private final List<String> TEST_CASE_UPD_WITH_USER_TABLE_FIELDS = Arrays.asList("id", "name", "email");

    public TestCaseServiceImpl(TestCaseDAO testCaseDAO, VariableValueDAO variableValueDAO, ActionInstanceDAO actionInstanceDAO, Pagination pagination) {
        this.testCaseDAO = testCaseDAO;
        this.variableValueDAO = variableValueDAO;
        this.actionInstanceDAO = actionInstanceDAO;
        this.pagination = pagination;
    }


    private <T> Predicate<T> distinctBy(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * @return map with actionId as key and variableDto without dataEntry as value
     */
    private Map<Long, Set<VariableDto>> getActionVariables(List<ActionInstanceJoined> actionInstanceJoinedList) {
        return actionInstanceJoinedList.stream()
                .collect(Collectors.groupingBy(ActionInstanceJoined::getId,
                        Collectors.mapping(ai -> VariableDto.builder()
                                .id(ai.getVariable().getId())
                                .name(ai.getVariable().getName())
                                .build(), Collectors.toSet())));
    }

    /**
     * @return map with actionInstanceId as key and variableDto with dataEntry as value
     */
    private Map<Long, Set<VariableDto>> getActionVariablesWithDataEntries(List<TestCaseStep> testCaseSteps) {
        return testCaseSteps.stream()
                .collect(Collectors.groupingBy(tcs -> tcs.getActionInstanceJoined().getId(),
                        Collectors.mapping(tcs -> VariableDto.builder()
                                .variableValueId(tcs.getActionInstanceJoined().getVariableValueId())
                                .id(tcs.getActionInstanceJoined().getVariable().getId())
                                .name(tcs.getActionInstanceJoined().getVariable().getName())
                                .dataEntry(tcs.getDataEntry())
                                .build(), Collectors.toSet())));
    }

    /**
     * get scenario steps that are compounds
     *
     * @return map with compound priority as a key and step as value
     */
    private List<ScenarioStepDto> getCompoundsByPriorities(List<ActionInstanceJoined> actionInstanceJoinedList,
                                                           Map<Long, Set<VariableDto>> actionsVariables) {
        Map<Integer, List<ActionInstanceJoined>> priorityCompound = actionInstanceJoinedList.stream()
                .filter(ai -> ai.getCompoundInstance() != null)
                .collect(Collectors.groupingBy(ai -> ai.getCompoundInstance().getPriority(),
                        Collectors.toList()));

        return priorityCompound.entrySet().stream()
                .map(e -> ScenarioStepDto.builder()
                        .priority(e.getKey())
                        .compound(e.getValue().get(0).getCompoundInstance().getCompound())
                        .actionDto(e.getValue().stream()
                                //filtering unique action instances by priority
                                .filter(distinctBy(ActionInstanceJoined::getPriority))
                                //sorting actions in compound by priorities in them
                                .sorted(Comparator.comparingLong(ActionInstanceJoined::getPriority))
                                .map(ai -> ActionDto.builder()
                                        .actionInstanceId(ai.getId())
                                        .name(ai.getAction().getActionName())
                                        .variables(new ArrayList<>(actionsVariables.getOrDefault(ai.getId(), new HashSet<>()))).build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * get scenario steps that are actions
     *
     * @return map with action priority as a key and step as value
     */
    private List<ScenarioStepDto> getActionsByPriorities(List<ActionInstanceJoined> actionInstanceJoinedList,
                                                         Map<Long, Set<VariableDto>> actionsVariables) {
        return actionInstanceJoinedList.stream()
                .filter(ai -> ai.getCompoundInstance() == null)
                .map(ai -> ScenarioStepDto.builder()
                        .priority(ai.getPriority())
                        .actionDto(Collections.singletonList(ActionDto.builder()
                                .actionInstanceId(ai.getId())
                                .name(ai.getAction().getActionName())
                                .variables(new ArrayList<>(actionsVariables.getOrDefault(ai.getId(), new HashSet<>()))).build()))
                        .build())
                .filter(distinctBy(ScenarioStepDto::getPriority))
                .collect(Collectors.toList());
    }

    private List<ScenarioStepDto> buildTestScenarioStep(List<ActionInstanceJoined> actionInstanceJoinedList, Map<Long, Set<VariableDto>> actionsVariables) {
        //getting compounds by priorities
        List<ScenarioStepDto> priorityCompound = getCompoundsByPriorities(actionInstanceJoinedList, actionsVariables);

        //getting actions that not in compounds by priorities
        List<ScenarioStepDto> priorityActionNotCompound = getActionsByPriorities(actionInstanceJoinedList, actionsVariables);

        //building scenario steps
        List<ScenarioStepDto> scenarioSteps = new ArrayList<>(priorityCompound);
        scenarioSteps.addAll(priorityActionNotCompound);
        scenarioSteps.sort(Comparator.comparing(ScenarioStepDto::getPriority));
        return scenarioSteps;
    }

    @Override
    @Transactional
    public void createTestCase(CreateUpdateTestCaseDto createUpdateTestCaseDto, Long userId) {

        TestCase testCase = TestCase.builder()
                .name(createUpdateTestCaseDto.getTestCaseName())
                .testScenarioId(createUpdateTestCaseDto.getTestScenarioId())
                .dataSetId(createUpdateTestCaseDto.getDataSetId())
                .projectId(createUpdateTestCaseDto.getProjectId())
                .userId(userId)
                .build();

        Long testCaseId = testCaseDAO.insert(testCase);
        System.out.println(createUpdateTestCaseDto.getVariableValues());
        variableValueDAO.insert(createUpdateTestCaseDto.getVariableValues(), testCaseId);

    }

    @Override
    public List<ScenarioStepDto> getTestScenarioStep(Long testCaseId) {

        List<ActionInstanceJoined> actionInstanceJoinedList = actionInstanceDAO.getActionInstanceJoinedByTestCaseId(testCaseId);

        //getting list if variables for each action
        Map<Long, Set<VariableDto>> actionsVariables = getActionVariables(actionInstanceJoinedList);

        return buildTestScenarioStep(actionInstanceJoinedList, actionsVariables);

    }

    @Override
    public TestCaseDto getTestCase(Long testCaseId) {

        List<TestCaseStep> testCaseSteps = testCaseDAO.getTestCaseSteps(testCaseId);

        List<ActionInstanceJoined> actionInstanceJoinedList = testCaseSteps.stream()
                .map(TestCaseStep::getActionInstanceJoined).collect(Collectors.toList());

        //getting list if variables for each action
        Map<Long, Set<VariableDto>> actionsVariables = getActionVariablesWithDataEntries(testCaseSteps);

        List<ScenarioStepDto> scenarioStepsWithData = buildTestScenarioStep(actionInstanceJoinedList, actionsVariables);

        return TestCaseDto.builder()
                .projectName(testCaseSteps.get(0).getProjectName())
                .projectLink(testCaseSteps.get(0).getProjectLink())
                .testCase(testCaseSteps.get(0).getTestCase())
                .scenarioStepsWithData(scenarioStepsWithData)
                .build();

    }

    @Override
    public List<TestCaseUpd> getAllTestCases() {
        return testCaseDAO.getTestCases();
    }

    /**
     * @return list of five TestCase objects those have the greatest number of subscribers.
     */
    @Override
    public List<TestCaseTopSubscribed> getFiveTopSubscribedTestCases() {
        List<TestCaseTopSubscribed> testCases = testCaseDAO.getTopFiveSubscribedTestCases();
        if(testCases.isEmpty()) {
            log.warn("IN getFiveTopSubscribedTestCases - no test cases found");
            return testCases;
        }
        log.info("IN getFiveTopSubscribedTestCases - test cases: {} found", testCases);
        return testCases;
    }

    @Override
    public Integer countTestCasesByProject(Integer pageSize, Long projectId) {
        return pagination.countPages(testCaseDAO.countTestCasesByProject(projectId), pageSize);
    }


    @Override
    public List<TestCaseUpd> getTestCases(Long projectID, Pageable pageable, String name) {
        pageable = pagination.replaceNullsUserPage(pageable);
        pagination.validate(pageable, TEST_CASE_UPD_TABLE_FIELDS);
        return testCaseDAO.getTestCasesPageSorted(projectID, pagination.formSqlPostgresPaginationPiece(pageable),
                replaceNullsForSearch(name));
    }

    @Override
    public List<TestCaseWithUserDto> getTestCasesWithUser(Long projectID, Pageable pageable, String name) {
        pageable = pagination.replaceNullsUserPage(pageable);
        pagination.validate(pageable, TEST_CASE_UPD_WITH_USER_TABLE_FIELDS);
        return testCaseDAO.getTestCasesWithUserPageSorted(projectID, pagination.formSqlPostgresPaginationPiece(pageable),
                replaceNullsForSearch(name));
    }

    @Transactional
    @Override
    public void updateTestCase(CreateUpdateTestCaseDto createUpdateTestCaseDto) {
        testCaseDAO.update(createUpdateTestCaseDto.getId(), createUpdateTestCaseDto.getTestCaseName());
        variableValueDAO.updateDataEntry(createUpdateTestCaseDto.getVariableValues());
    }

    @Override
    public void addSubscriber(Long testCaseId, Long userId) {
        testCaseDAO.addSubscriber(testCaseId, userId);
    }

    @Override
    public Boolean isFollowedByUser(Long testCaseId, Long userId) {
        return testCaseDAO.isFollowedByUser(testCaseId, userId);
    }

    @Override
    public void removeSubscriber(Long testCaseId, Long userId) {
        testCaseDAO.removeSubscriber(testCaseId, userId);
    }

    @Override
    public void archiveTestCase(Long projectId) {
        testCaseDAO.updateIsArchivedField(projectId, true);
    }

    @Override
    public void unarchiveTestCase(Long projectId) {
        testCaseDAO.updateIsArchivedField(projectId, false);

    }

    private String replaceNullsForSearch(String val) {
        return val == null ? "%" : val;
    }
}
