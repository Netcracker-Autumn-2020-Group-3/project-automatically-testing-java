package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionsCountsByStartDatesDto;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController {

    private final TestCaseService testCaseService;
    private final TestCaseExecService testCaseExecService;

    public DashboardController(TestCaseService testCaseService, TestCaseExecService testCaseExecService) {
        this.testCaseService = testCaseService;
        this.testCaseExecService = testCaseExecService;
    }

    @GetMapping("/top-subscribed-test-cases")
    public ResponseEntity<?> getTopFiveSubscribedTestCases() {
        return ResponseEntity.ok(testCaseService.getFiveTopSubscribedTestCases());
    }

    @GetMapping("/test-case-executions-by-dates")
    public List<TestCaseExecutionsCountsByStartDatesDto> testCaseExecutionsByDates(@RequestParam Integer numberOfDays){
        return testCaseExecService.getExecutionsByDatesForLastDays(numberOfDays);
    }
}
