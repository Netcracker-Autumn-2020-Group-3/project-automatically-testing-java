package ua.netcracker.group3.automaticallytesting.execution.action.impl;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import ua.netcracker.group3.automaticallytesting.execution.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.ContextVariable;
import ua.netcracker.group3.automaticallytesting.model.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class DropDownActionExecutable implements ActionExecutable {

    private final Map<Optional<ContextVariable>, Status> resultActionExecution = new HashMap<>();


    @Override
    public Map<Optional<ContextVariable>, Status> executeAction(WebDriver driver, Map<String, String> variableValues) {
        final String MENU = "menu xpath";
        final String MENU_ELEMENT = "menu element xpath";
        Status actionExecution;
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath(variableValues.get(MENU))));
            Action moveTo = actions.build();
            moveTo.perform();
            driver.findElement(By.xpath(variableValues.get(MENU_ELEMENT))).click();
            actionExecution = Status.PASSED;
       }catch (NoSuchElementException exception){
           log.error("No such element like {} ",variableValues.get(MENU));
           actionExecution = Status.FAILED;
        }

        resultActionExecution.put(Optional.empty(),actionExecution);
        return resultActionExecution;
    }
}
