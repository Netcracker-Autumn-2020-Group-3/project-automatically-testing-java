package ua.netcracker.group3.automaticallytesting.execution;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.VariableDto;
import ua.netcracker.group3.automaticallytesting.execution.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.ContextVariable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.ClickActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.TypeActionExecutable;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestCaseExecutionServiceSelenium implements TestCaseExecutionService {

    private ActionExecutionDAO actionExecutionDAO;
    private final List<ActionExecution> actionExecutions = new ArrayList<>();

    //TODO make actionExecutionController to get all the actions` status
    //TODO on front make page of all actions` execution

    @Autowired
    public TestCaseExecutionServiceSelenium(ActionExecutionDAO actionExecutionDAO){
        this.actionExecutionDAO = actionExecutionDAO;
    }


    private final Map<String, ActionExecutable> actions = new HashMap<String, ActionExecutable>() {{
        put("click sign in", new ClickActionExecutable());
        put("click login", new ClickActionExecutable());
        put("enter login", new TypeActionExecutable());
        put("enter password", new TypeActionExecutable());
    }};

    public TestCaseExecutionServiceSelenium() {
         //System.setProperty("webdriver.chrome.driver", "D:\\netcracker\\chrome-driver87\\chromedriver.exe");
        // System.setProperty("webdriver.chrome.driver", "C:\\webdriver86\\chromedriver.exe");
         System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
    }

    @Override
    public List<String> executeTestCase(TestCaseDto testCaseDto,Long testCaseExecutionId) {

        WebDriver driver = new ChromeDriver();
        Map<Long, ContextVariable> contextVariables = new HashMap<>();
        List<ScenarioStepDto> scenarioStepDtoList = testCaseDto.getScenarioStepsWithData();

        log.info("Test case execution started");

        driver.get(testCaseDto.getProjectLink());
        driver.manage().window().maximize();

        scenarioStepDtoList.forEach(step -> {
            step.getActionDto().forEach(actionDto -> {
                actions.get(actionDto.getName())
                        .executeAction(driver, variableDtosToVariableValues(actionDto.getVariables()))
                        .forEach((contextVariable, status) -> {
                            actionExecutions.add(ActionExecution.builder()
                                    .testCaseExecutionId(testCaseExecutionId)
                                    .actionInstanceId(actionDto.getId())
                                    .status(status)
                                    .build());
                            contextVariable.ifPresent(cv ->
                                    contextVariables.put(actionDto.getId(), cv));
                            log.info("action STATUS of {} is " + status, actionDto.getName());
                        });
                try {
                    Thread.sleep(1250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        driver.close();
        log.info("Test case execution finished");

        List<String> statusActionExecutionsResult = statusValuesForTestExecution(actionExecutions);
        createActionExecutions(actionExecutions);
        return statusActionExecutionsResult;
    }

    private Map<String, String> variableDtosToVariableValues(List<VariableDto> variables) {
        return variables.stream().collect(Collectors.toMap(VariableDto::getName, v -> v.getDataEntry().getValue()));
    }

    private List<String> statusValuesForTestExecution(List<ActionExecution> actionExecutionList){
        return actionExecutionList.stream()
                .map(ActionExecution::getStatus)
                .collect(Collectors.toList());
    }

    private void createActionExecutions(List<ActionExecution> actionExecutionList){
        actionExecutionDAO.addActionExecution(actionExecutionList);
    }
}