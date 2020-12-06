package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.ActionInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseDAO;
import ua.netcracker.group3.automaticallytesting.dao.VariableValueDAO;
import ua.netcracker.group3.automaticallytesting.dto.*;
import ua.netcracker.group3.automaticallytesting.model.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;
import ua.netcracker.group3.automaticallytesting.model.VariableValue;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseDAO testCaseDAO;
    private final VariableValueDAO variableValueDAO;
    private final ActionInstanceDAO actionInstanceDAO;

    public TestCaseServiceImpl(TestCaseDAO testCaseDAO, VariableValueDAO variableValueDAO, ActionInstanceDAO actionInstanceDAO) {
        this.testCaseDAO = testCaseDAO;
        this.variableValueDAO = variableValueDAO;
        this.actionInstanceDAO = actionInstanceDAO;
    }

    @Transactional
    public void createTestCase(CreateTestCaseDto createTestCaseDto, Long userId) {

        TestCase testCase = TestCase.builder().name(createTestCaseDto.getTestCaseName())
                .testScenarioId(createTestCaseDto.getTestScenarioId())
                .dataSetId(createTestCaseDto.getDataSetId())
                .projectId(createTestCaseDto.getProjectId())
                .userId(userId)
                .build();

        Long testCaseId = testCaseDAO.insert(testCase);

        variableValueDAO.insert(createTestCaseDto.getVariableValues(), testCaseId);

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
                .collect(Collectors.groupingBy(ai -> ai.getAction().getActionId(),
                        Collectors.mapping(ai -> VariableDto.builder()
                                .id(ai.getVariable().getId())
                                .name(ai.getVariable().getName())
                                .build(), Collectors.toSet())));
    }

    /**
     * @return map with actionId as key and variableDto with dataEntry as value
     */
    private Map<Long, Set<VariableDto>> getActionVariablesWithDataEntries(List<TestCaseStep> testCaseSteps) {
        return testCaseSteps.stream()
                .collect(Collectors.groupingBy(tcs -> tcs.getActionInstanceJoined().getAction().getActionId(),
                        Collectors.mapping(tcs -> VariableDto.builder()
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
    public List<ScenarioStepDto> getCompoundsByPriorities(List<ActionInstanceJoined> actionInstanceJoinedList,
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
                                        .id(ai.getId())
                                        .name(ai.getAction().getActionName())
                                        .variables(new ArrayList<>(actionsVariables.getOrDefault(ai.getAction().getActionId(), new HashSet<>()))).build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * get scenario steps that are actions
     *
     * @return map with action priority as a key and step as value
     */
    public List<ScenarioStepDto> getActionsByPriorities(List<ActionInstanceJoined> actionInstanceJoinedList,
                                                        Map<Long, Set<VariableDto>> actionsVariables) {
        return actionInstanceJoinedList.stream()
                .filter(ai -> ai.getCompoundInstance() == null)
                .map(ai -> ScenarioStepDto.builder()
                        .priority(ai.getPriority())
                        .actionDto(Collections.singletonList(ActionDto.builder()
                                .id(ai.getId())
                                .name(ai.getAction().getActionName())
                                .variables(new ArrayList<>(actionsVariables.getOrDefault(ai.getAction().getActionId(), new HashSet<>()))).build()))
                        .build())
                .filter(distinctBy(ScenarioStepDto::getPriority))
                .collect(Collectors.toList());
    }

    public List<ScenarioStepDto> buildTestScenarioStep(List<ActionInstanceJoined> actionInstanceJoinedList, Map<Long, Set<VariableDto>> actionsVariables) {
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
}
