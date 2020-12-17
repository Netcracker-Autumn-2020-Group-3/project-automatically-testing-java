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
public class CheckBoxCheckActionExecutable implements ActionExecutable {

    private final String CHECKBOX = "checkbox xpath";

    @Override
    public Optional<ContextVariable> execute(WebDriver driver, Map<String, String> variableValues) {
        return Optional.empty();
    }

    @Override
    public Map<Optional<ContextVariable>, String> executeAction(WebDriver driver, Map<String, String> variableValues) {
        try {
            if (!driver.findElement(By.xpath(CHECKBOX)).isSelected()) {
                driver.findElement(By.xpath(CHECKBOX)).click();
            }
            return new HashMap<Optional<ContextVariable>, String>() {{
                put(Optional.empty(), Status.PASSED.name());
            }};
        } catch (NoSuchElementException exception) {
            log.error("No such element like {} ", variableValues.get(CHECKBOX));
            return new HashMap<Optional<ContextVariable>, String>() {{
                put(Optional.empty(), Status.FAILED.name());
            }};
        }

    }
}
