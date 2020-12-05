package ua.netcracker.group3.automaticallytesting.testcaseexec.action.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ua.netcracker.group3.automaticallytesting.testcaseexec.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.testcaseexec.action.ContextVariable;

import java.util.Map;
import java.util.Optional;

public class ClickActionExecutable implements ActionExecutable {

    private final String BUTTON = "button xpath";

    @Override
    public Optional<ContextVariable> execute(WebDriver driver, Map<String, String> variableValues) {
        System.out.println("click xpath " + variableValues.get(BUTTON));
        driver.findElement(By.xpath(variableValues.get(BUTTON))).click();
        return Optional.empty();
    }
}
