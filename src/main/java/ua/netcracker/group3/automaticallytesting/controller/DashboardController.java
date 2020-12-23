package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.dto.UserCountDto;
import ua.netcracker.group3.automaticallytesting.service.DashboardService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.dto.GroupedTestCaseExecutionDto;
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
    private final DashboardService dashboardService;

    public DashboardController(TestCaseService testCaseService, TestCaseExecService testCaseExecService, ActionExecutionService actionExecutionService, DashboardService dashboardService) {
        this.testCaseService = testCaseService;
        this.testCaseExecService = testCaseExecService;
        this.actionExecutionService = actionExecutionService;
        this.dashboardService = dashboardService;

    }

    @GetMapping("/top-subscribed-test-cases")
    public ResponseEntity<?> getTopFiveSubscribedTestCases() {
        return ResponseEntity.ok(testCaseService.getFiveTopSubscribedTestCases());
    }

    @GetMapping("/test-case-executions-by-dates")
    public List<TestCaseExecutionsCountsByStartDatesDto> testCaseExecutionsByDates(@RequestParam Integer numberOfDays){
        return testCaseExecService.getExecutionsByDatesForLastDays(numberOfDays);
    }

    /**
     * Returns the list of GroupedTestCaseExecutionDto
     * Grouped by most executable test cases
     * Method is needed for chart on angular
     * @return list of GroupedTestCaseExecutionDto
     */
    @GetMapping("/test-case-execution/grouped-number")
    public List<GroupedTestCaseExecutionDto> getGroupedTestCaseExecutionNumber(){
        return testCaseExecService.getGroupedTestCaseExecution();
    }

    /**
     * Returns the number of actions executions
     * Grouped by data
     * @param status of actions executions
     * @return number of actions executions
     */
    @GetMapping("/action-execution/{status}")
    public List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(@PathVariable("status") String status) {
        return actionExecutionService.getActionExecutionPassedFailed(status);
    }

    /**
     * Returns UserCountDto:
     * It contains fields of Long type: userCount, adminCount, managerCount, engineerCount
     */
    @GetMapping("/user-count-by-role")
    public UserCountDto getUserCountByRole() {
        return dashboardService.getCountOfUsersByRole();
    }
}
