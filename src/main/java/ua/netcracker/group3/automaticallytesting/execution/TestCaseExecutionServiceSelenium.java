package ua.netcracker.group3.automaticallytesting.execution;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.VariableDto;
import ua.netcracker.group3.automaticallytesting.execution.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.ContextVariable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.ClickActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.impl.TypeActionExecutable;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestCaseExecutionServiceSelenium implements TestCaseExecutionService {

    private final Map<String, ActionExecutable> actions = new HashMap<String, ActionExecutable>() {{
        put("click sign in", new ClickActionExecutable());
        put("click login", new ClickActionExecutable());
        put("enter login", new TypeActionExecutable());
        put("enter password", new TypeActionExecutable());
    }};

    public TestCaseExecutionServiceSelenium() {
         System.setProperty("webdriver.chrome.driver", "D:\\netcracker\\chrome-driver87\\chromedriver.exe");
        // System.setProperty("webdriver.chrome.driver", "C:\\webdriver86\\chromedriver.exe");
    }

    @Override
    public Map<Long, ContextVariable> executeTestCase(TestCaseDto testCaseDto) {

        WebDriver driver = new ChromeDriver();
        Map<Long, ContextVariable> contextVariables = new HashMap<>();
        List<ScenarioStepDto> scenarioStepDtoList = testCaseDto.getScenarioStepsWithData();

        log.info("Test case execution started");

        driver.get(testCaseDto.getProjectLink());


        driver.manage().window().maximize();


        scenarioStepDtoList.forEach(step -> {
            step.getActionDto().forEach(actionDto -> {
                actions.get(actionDto.getName())
                        .execute(driver, variableDtosToVariableValues(actionDto.getVariables()))
                        .ifPresent(cv ->
                                contextVariables.put(actionDto.getId(), cv));
                log.info("{} action is fine", actionDto.getName());
                try {
                    Thread.sleep(1250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        //driver.close();

        log.info("Test case execution started");
        return contextVariables;
    }

    private Map<String, String> variableDtosToVariableValues(List<VariableDto> variables) {
        return variables.stream().collect(Collectors.toMap(VariableDto::getName, v -> v.getDataEntry().getValue()));
    }
}
