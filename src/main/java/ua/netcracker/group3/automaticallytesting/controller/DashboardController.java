package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.execution.TestCaseExecutionService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;
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
    private final TestCaseExecService testCaseExecService;
    private final ActionExecutionService actionExecutionService;

    public DashboardController(TestCaseService testCaseService, TestCaseExecService testCaseExecService, ActionExecutionService actionExecutionService) {
        this.testCaseService = testCaseService;
        this.testCaseExecService = testCaseExecService;
        this.actionExecutionService = actionExecutionService;
    }

    @GetMapping("/top-subscribed-test-cases")
    public ResponseEntity<?> getTopFiveSubscribedTestCases() {
        return ResponseEntity.ok(testCaseService.getFiveTopSubscribedTestCases());
    }

    @GetMapping("/test-case-executions-by-dates")
    public List<TestCaseExecutionsCountsByStartDatesDto> testCaseExecutionsByDates(@RequestParam Integer numberOfDays){
        return testCaseExecService.getExecutionsByDatesForLastDays(numberOfDays);
    }

    @GetMapping("/test-case-execution/grouped-number")
    public List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecutionNumber(){
        return testCaseExecService.getGroupedTestCaseExecution();
    }

    @GetMapping("/action-execution/{status}")
    public List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(@PathVariable("status") String status) {
        return actionExecutionService.getActionExecutionPassedFailed(status);
    }
}
