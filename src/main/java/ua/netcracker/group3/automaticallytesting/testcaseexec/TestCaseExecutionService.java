package ua.netcracker.group3.automaticallytesting.testcaseexec;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dto.ActionDto;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.VariableDto;
import ua.netcracker.group3.automaticallytesting.testcaseexec.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.testcaseexec.action.ContextVariable;
import ua.netcracker.group3.automaticallytesting.testcaseexec.action.impl.ClickActionExecutable;
import ua.netcracker.group3.automaticallytesting.testcaseexec.action.impl.TypeActionExecutable;

import java.util.*;

@Service
public class TestCaseExecutionService {

    Map<String, ActionExecutable> actions = new HashMap<>();


    public TestCaseExecutionService() {
       // System.setProperty("webdriver.chrome.driver", "D:\\netcracker\\chrome-driver87\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");

        actions.put("click sign in", new ClickActionExecutable());
        actions.put("click login", new ClickActionExecutable());
        actions.put("enter login", new TypeActionExecutable());
        actions.put("enter password", new TypeActionExecutable());
    }

    public Map<Long, ContextVariable> executeTestCase(TestCaseDto testCaseDto, String projectLink) {

        WebDriver driver = new ChromeDriver();

        Map<Long, ContextVariable> contextVariables = new HashMap<>();

        driver.get(projectLink);
        driver.manage().window().maximize();
        //System.out.println("exec");
        System.out.println("aCTIONS " + actions.toString());

        List<ScenarioStepDto> scenarioStepDtoList = testCaseDto.getScenarioStepsWithData();
        System.out.println(scenarioStepDtoList);
        for (ScenarioStepDto step : scenarioStepDtoList) {
            for (ActionDto actionDto : step.getActionDto()) {
                Map<String, String> variableValues = new HashMap<>();
                for (VariableDto variableDto : actionDto.getVariables()) {
                    System.out.println("MAP " + variableDto.getName() + " " + variableDto.getDataEntry().getValue());
                    variableValues.put(variableDto.getName(), variableDto.getDataEntry().getValue());
                }
                actions.get(actionDto.getName()).execute(driver, variableValues).ifPresent(cv ->
                        contextVariables.put(actionDto.getId(), cv));

                System.out.println("CONTEXT " + contextVariables.toString());

              //  System.out.println(actionDto.getName() + " is fine");
            }

        }
        //driver.close();
        return contextVariables;
    }
}
