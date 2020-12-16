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

   // private final String MENU = "menu xpath";
    private final String MENU = "/html/body/div[1]/header/div[7]/details/details-menu";
    //private final String MENU_ELEMENT = "menu element xpath";
    private final String MENU_ELEMENT = "/html/body/div[1]/header/div[7]/details/details-menu/a[1]";
    private final Map<Optional<ContextVariable>, String> resultActionExecution = new HashMap<>();
    private String actionExecution;

    public DropDownActionExecutable() {
    }

    @Override
    public Optional<ContextVariable> execute(WebDriver driver, Map<String, String> variableValues) {
        return Optional.empty();
    }

    @Override
    public Map<Optional<ContextVariable>, String> executeAction(WebDriver driver, Map<String, String> variableValues) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath(MENU)));
            Action moveTo = actions.build();
            moveTo.perform();
            driver.findElement(By.xpath(MENU_ELEMENT)).click();
        }catch (NoSuchElementException exception){
            log.error("No such element like {} ",variableValues.get(MENU));
            actionExecution = Status.FAILED.name();
        }

        resultActionExecution.put(Optional.empty(),actionExecution);
        return resultActionExecution;
    }
}
