package ua.netcracker.group3.automaticallytesting.execution;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;import org.springframework.beans.factory.annotation.Autowired;
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

import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.SseService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TestCaseExecutionServiceSelenium implements TestCaseExecutionService {

    private ActionExecutionDAO actionExecutionDAO;
    private List<ActionExecution> actionExecutions;
    private SseService sseService;

    private Status actionStatus = Status.PASSED;

    @Autowired
    public TestCaseExecutionServiceSelenium(ActionExecutionDAO actionExecutionDAO, SseService sseService){
        this.actionExecutionDAO = actionExecutionDAO;
        this.sseService = sseService;
        //System.setProperty("webdriver.chrome.driver", "D:\\netcracker\\chrome-driver87\\chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "chrome-driver87\\chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "chromedriver_linux64/chromedriver");
        //System.setProperty("webdriver.geckodriver.driver", "/app/vendor/geckodriver/geckodriver");
        //System.setProperty("webdriver.gecko.driver", "/app/vendor/geckodriver/geckodriver");
        //System.setProperty("webdriver.chrome.driver", "F:\\netcracker\\chromedriver.exe");
    }

    private final Map<String, ActionExecutable> actions = new HashMap<String, ActionExecutable>() {{
        put("click sign in", new ClickActionExecutable());
        put("click login", new ClickActionExecutable());
        put("enter login", new TypeActionExecutable());
        put("enter password", new TypeActionExecutable());
        // main actions
        put("click", new ClickActionExecutable());
        put("input", new TypeActionExecutable());
        put("click on drop down menu element", new DropDownActionExecutable());
        put("check checkbox", new CheckBoxCheckActionExecutable());
        put("uncheck checkbox", new CheckBoxUncheckActionExecutable());
    }};

    @Override
    public List<String> executeTestCase(TestCaseDto testCaseDto,Long testCaseExecutionId) {

        actionExecutions = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--lang=en");

        WebDriver driver = new ChromeDriver(options);
        //WebDriver driver = new ChromeDriver();

        Map<Long, ContextVariable> contextVariables = new HashMap<>();
        List<ScenarioStepDto> scenarioStepDtoList = testCaseDto.getScenarioStepsWithData();

        log.info("Test case execution started");

        driver.get(testCaseDto.getProjectLink());
        //driver.manage().window().maximize();

        scenarioStepDtoList.forEach(step -> {
            step.getActionDto().forEach(actionDto -> {
                if (actionStatus == Status.PASSED) {
                    actions.get(actionDto.getName())
                            .executeAction(driver, variableDtosToVariableValues(actionDto.getVariables()))
                            .forEach((contextVariable, status) -> {
                                actionStatus = status;
                                fillActionExecution(testCaseExecutionId,actionDto,status,contextVariable,contextVariables);});
                }else{
                    fillActionExecution(testCaseExecutionId,actionDto,Status.NOTSTARTED,Optional.empty(),contextVariables);
                }

            });
        });

        driver.close();
        log.info("Test case execution finished");

        List<String> statusActionExecutionsResult = statusValuesForTestExecution(actionExecutions);
        createActionExecutions(actionExecutions);
        return statusActionExecutionsResult;
    }

    private void fillActionExecution(Long testCaseExecutionId, ActionDto actionDto, Status status, Optional<ContextVariable> contextVariable, Map<Long, ContextVariable> contextVariables) {
        actionExecutions.add(ActionExecution.builder()
                .testCaseExecutionId(testCaseExecutionId)
                .actionInstanceId(actionDto.getActionInstanceId())
                .status(status.name())
                .build());
        contextVariable.ifPresent(cv ->
                contextVariables.put(actionDto.getActionInstanceId(), cv));
        log.info("action STATUS of {} is " + status, actionDto.getName());
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
