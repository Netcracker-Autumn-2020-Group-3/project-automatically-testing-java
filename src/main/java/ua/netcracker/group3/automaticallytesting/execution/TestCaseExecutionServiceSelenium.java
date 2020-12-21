package ua.netcracker.group3.automaticallytesting.execution;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionDto;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.VariableDto;
import ua.netcracker.group3.automaticallytesting.execution.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.ContextVariable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.CheckBoxCheckActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.CheckBoxUncheckActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.ClickActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.DropDownActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.TypeActionExecutable;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.model.Status;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestCaseExecutionServiceSelenium implements TestCaseExecutionService {

    private final ActionExecutionDAO actionExecutionDAO;
    private List<ActionExecution> actionExecutions;
    private Status actionStatus;

    @Autowired
    public TestCaseExecutionServiceSelenium(ActionExecutionDAO actionExecutionDAO){
        this.actionExecutionDAO = actionExecutionDAO;
    }

    private final Map<String, ActionExecutable> actions = new HashMap<String, ActionExecutable>() {{
        /*put("click sign in", new ClickActionExecutable());
        put("click login", new ClickActionExecutable());
        put("enter login", new TypeActionExecutable());
        put("enter password", new TypeActionExecutable());*/
        put("click", new ClickActionExecutable());
        put("input", new TypeActionExecutable());
        put("click on drop down menu element", new DropDownActionExecutable());
        put("check checkbox", new CheckBoxCheckActionExecutable());
        put("uncheck checkbox", new CheckBoxUncheckActionExecutable());
    }};

    /**
     * @param testCaseDto
     * @param testCaseExecutionId
     * @return
     */
    @Override
    public List<String> executeTestCase(TestCaseDto testCaseDto,Long testCaseExecutionId) {

        Map<Long, ContextVariable> contextVariables = new HashMap<>();
        List<ScenarioStepDto> scenarioStepDtoList = testCaseDto.getScenarioStepsWithData();
        actionExecutions = new ArrayList<>();
        actionStatus = Status.PASSED;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--lang=en");
        WebDriver driver = new ChromeDriver(options);

        log.info("Test case execution started");
        driver.get(testCaseDto.getProjectLink());

        scenarioStepDtoList.forEach(step -> {
            step.getActionDto().forEach(actionDto -> {
                executeScenarioAction(actionDto,driver,testCaseExecutionId,contextVariables);
            });
        });

        driver.close();
        log.info("Test case execution finished");

        List<String> statusActionExecutionsResult = statusValuesForTestExecution(actionExecutions);
        createActionExecutions(actionExecutions);
        log.info("Action Executions were added to DB successfully : {}",actionExecutions);
        return statusActionExecutionsResult;
    }

    /**
     * @param actionDto
     * @param driver
     * @param testCaseExecutionId
     * @param contextVariables
     */
    private void executeScenarioAction(ActionDto actionDto,WebDriver driver,Long testCaseExecutionId,Map<Long, ContextVariable> contextVariables){
        if (actionStatus == Status.PASSED) {
            actions.get(actionDto.getName())
                    .executeAction(driver, variableDtosToVariableValues(actionDto.getVariables()))
                    .forEach((contextVariable, status) -> {
                        actionStatus = status;
                        log.info("Action STATUS of {} is {}",actionDto.getName(),status);
                        fillActionExecution(testCaseExecutionId,actionDto,status,contextVariable,contextVariables);});
        }else{
            fillActionExecution(testCaseExecutionId,actionDto,Status.NOTSTARTED,Optional.empty(),contextVariables);
        }
    }


    /**
     * @param testCaseExecutionId
     * @param actionDto
     * @param status
     * @param contextVariable
     * @param contextVariables
     */
    private void fillActionExecution(Long testCaseExecutionId, ActionDto actionDto,
                                     Status status, Optional<ContextVariable> contextVariable,
                                     Map<Long, ContextVariable> contextVariables) {
        actionExecutions.add(ActionExecution.builder()
                .testCaseExecutionId(testCaseExecutionId)
                .actionInstanceId(actionDto.getActionInstanceId())
                .status(status.name())
                .build());
        contextVariable.ifPresent(cv ->
                contextVariables.put(actionDto.getActionInstanceId(), cv));
    }

    /**
     * @param variables
     * @return
     */
    private Map<String, String> variableDtosToVariableValues(List<VariableDto> variables) {
        return variables.stream().collect(Collectors.toMap(VariableDto::getName, v -> v.getDataEntry().getValue()));
    }

    /**
     * @param actionExecutionList
     * @return
     */
    private List<String> statusValuesForTestExecution(List<ActionExecution> actionExecutionList){
        return actionExecutionList.stream()
                .map(ActionExecution::getStatus)
                .collect(Collectors.toList());
    }

    /**
     * @param actionExecutionList
     */
    private void createActionExecutions(List<ActionExecution> actionExecutionList){
        actionExecutionDAO.addActionExecution(actionExecutionList);
    }
}