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
public class TypeActionExecutable implements ActionExecutable {

    private final String INPUT_ELEMENT = "input xpath";
    private final String TEXT = "text";
    private final Map<Optional<ContextVariable>, Status> resultActionExecution = new HashMap<>();
    private Status actionExecution;

    public TypeActionExecutable(){
    }


    @Override
    public Map<Optional<ContextVariable>, Status> executeAction(WebDriver driver, Map<String, String> variableValues) {
        try {
            driver.findElement(By.xpath(variableValues.get(INPUT_ELEMENT)))
                    .sendKeys(variableValues.get(TEXT));
            actionExecution = Status.PASSED;
        }catch (NoSuchElementException exception){
            log.error("No such element like {} ",variableValues.get(INPUT_ELEMENT));
            actionExecution = Status.FAILED;
        }
        resultActionExecution.put(Optional.empty(),actionExecution);
        return resultActionExecution;
    }
}
