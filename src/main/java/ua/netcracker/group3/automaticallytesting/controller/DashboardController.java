package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.ActionExecutionPassedFailed;
import ua.netcracker.group3.automaticallytesting.service.ActionExecutionService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController {

    private final TestCaseService testCaseService;
    private final ActionExecutionService actionExecutionService;

    public DashboardController(TestCaseService testCaseService, ActionExecutionService actionExecutionService) {
        this.testCaseService = testCaseService;
        this.actionExecutionService = actionExecutionService;
    }

    @GetMapping("/top-subscribed-test-cases")
    public ResponseEntity<?> getTopFiveSubscribedTestCases() {
        return ResponseEntity.ok(testCaseService.getFiveTopSubscribedTestCases());
    }

    @GetMapping("/action-execution/{status}")
    public List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(@PathVariable("status") String status) {
        return actionExecutionService.getActionExecutionPassedFailed(status);
    }
}
