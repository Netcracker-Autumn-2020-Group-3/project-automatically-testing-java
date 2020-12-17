package ua.netcracker.group3.automaticallytesting.execution.action.impl;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ua.netcracker.group3.automaticallytesting.execution.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.ContextVariable;
import ua.netcracker.group3.automaticallytesting.model.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class ClickActionExecutable implements ActionExecutable {

    private final String BUTTON = "button xpath";
    private final Map<Optional<ContextVariable>, String> resultActionExecution = new HashMap<>();
    private String actionExecution;

    public ClickActionExecutable(){
    }



    @Override
    public Map<Optional<ContextVariable>, String> executeAction(WebDriver driver, Map<String, String> variableValues) {
        log.info("fine!");
        try {
            driver.findElement(By.xpath(variableValues.get(BUTTON))).click();
            actionExecution = Status.PASSED.name();
        }catch (NoSuchElementException exception){
            log.error("No such element like {} ",variableValues.get(BUTTON));
            actionExecution = Status.FAILED.name();
        }

        resultActionExecution.put(Optional.empty(),actionExecution);
        return resultActionExecution;
    }




}
