package ua.netcracker.group3.automaticallytesting.execution.action.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ua.netcracker.group3.automaticallytesting.execution.action.ActionExecutable;
import ua.netcracker.group3.automaticallytesting.execution.action.ContextVariable;

import java.util.Map;
import java.util.Optional;

public class TypeActionExecutable implements ActionExecutable {

    private final String INPUT_ELEMENT = "input xpath";
    private final String TEXT = "text";

    @Override
    public Optional<ContextVariable> execute(WebDriver driver, Map<String, String> variableValues) {
        System.out.println("input xpath " + variableValues.get(INPUT_ELEMENT));
        System.out.println("text " + variableValues.get(TEXT));
        driver
                .findElement(By.xpath(variableValues.get(INPUT_ELEMENT)))
                .sendKeys(variableValues.get(TEXT));
        return Optional.empty();
    }
}
