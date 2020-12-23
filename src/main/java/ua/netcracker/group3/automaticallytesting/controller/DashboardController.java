package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.model.Role;
import ua.netcracker.group3.automaticallytesting.service.DashboardService;
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
import ua.netcracker.group3.automaticallytesting.service.DashboardService;


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

    // role-id:
    // 1: ADMIN
    // 2: MANAGER
    // 3: ENGINEER
    @GetMapping("/user-count")
    public ResponseEntity<?> getUsersCount(@RequestParam(value = "role-id", required = false) String roleID) {
        Integer count = 0;
        if (roleID == null) {
            count = dashboardService.getCountOfUsers();
        }
        else if(roleID.equals("1")) {
            count = dashboardService.getCountOfUsers(Role.ADMIN.toString());
        }
        else if(roleID.equals("2")) {
            count = dashboardService.getCountOfUsers(Role.MANAGER.toString());
        }
        else if (roleID.equals("3")) {
            count = dashboardService.getCountOfUsers(Role.ENGINEER.toString());
        }

        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
