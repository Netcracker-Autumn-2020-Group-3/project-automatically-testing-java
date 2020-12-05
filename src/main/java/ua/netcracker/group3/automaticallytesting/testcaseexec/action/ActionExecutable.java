package ua.netcracker.group3.automaticallytesting.testcaseexec.action;

import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ActionExecutable {

    Optional<ContextVariable> execute(WebDriver driver, Map<String, String> variableValues);
}
