package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionInstanceDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionDto;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.VariableDto;
import ua.netcracker.group3.automaticallytesting.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ActionInstanceService {

    ActionInstanceDAO actionInstanceDAO;

    @Autowired
    public ActionInstanceService(ActionInstanceDAO actionInstanceDAO) {
        this.actionInstanceDAO = actionInstanceDAO;
    }


    public <T> Predicate<T> distinctBy(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public List<ScenarioStepDto> getTestScenarioStep(Long testCaseId) {

        List<ActionInstanceJoined> actionInstanceJoinedList = actionInstanceDAO.getActionInstanceJoinedByTestCaseId(testCaseId);

        //getting list if variables for each action
        Map<Long, Set<VariableDto>> actionsVariables = actionInstanceJoinedList.stream()
                .collect(Collectors.groupingBy(ai -> ai.getAction().getActionId(),
                        Collectors.mapping(ai -> VariableDto.builder()
                                .id(ai.getVariable().getId())
                                .name(ai.getVariable().getName()).build(), Collectors.toSet())));

        //getting compounds by priorities
        Map<Integer, List<ActionInstanceJoined>> priorityCompound = actionInstanceJoinedList.stream()
                .filter(ai -> ai.getCompoundInstance() != null)
                .collect(Collectors.groupingBy(ai -> ai.getCompoundInstance().getPriority(), Collectors.toList()));

        //getting actions that not in compounds by priorities
        Map<Integer, ActionInstanceJoined> priorityActionNotCompound = actionInstanceJoinedList.stream()
                .filter(ai -> ai.getCompoundInstance() == null)
                .collect(Collectors.toMap(ActionInstanceJoined::getPriority, ai -> ai));

        //building scenario steps
        List<ScenarioStepDto> scenarioSteps = new ArrayList<>();
        for (int i = 1; i <= Math.max(priorityActionNotCompound.keySet().stream().max(Comparator.comparingLong(k -> k)).orElse(0),
                priorityCompound.keySet().stream().max(Comparator.comparingLong(k -> k)).orElse(0)); i++) {

            ScenarioStepDto ssd = new ScenarioStepDto();
            ssd.setPriority(i);

            //if i-th step is not compound
            if (priorityActionNotCompound.containsKey(i)) {
                ActionInstanceJoined ai = priorityActionNotCompound.get(i);
                ssd.setActionDto(Collections.singletonList(ActionDto.builder()
                        .id(ai.getId())
                        .name(ai.getAction().getActionName())
                        .variables(new ArrayList<>(actionsVariables.getOrDefault(ai.getAction().getActionId(), new HashSet<>()))).build()));
            }
            //if i-th step is compound
            else {
                List<ActionInstanceJoined> compoundActions = priorityCompound.get(i);
                ssd.setCompound(compoundActions.get(0).getCompoundInstance().getCompound());
                ssd.setActionDto(compoundActions.stream()
                        .filter(distinctBy(ActionInstanceJoined::getPriority)) //filtering unique action instances by priority
                        .sorted(Comparator.comparingLong(ActionInstanceJoined::getPriority)) //sorting actions in compound by priorities in them
                        .map(ai -> ActionDto.builder()
                                .id(ai.getId())
                                .name(ai.getAction().getActionName())
                                .variables(new ArrayList<>(actionsVariables.getOrDefault(ai.getAction().getActionId(), new HashSet<>()))).build())
                        .collect(Collectors.toList()));

            }
            scenarioSteps.add(ssd);
        }
        return scenarioSteps;

    }
}

