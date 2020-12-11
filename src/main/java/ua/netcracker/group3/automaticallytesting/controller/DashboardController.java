package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;

@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController {

    private final TestCaseService testCaseService;

    public DashboardController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping("/top-subscribed-test-cases")
    public ResponseEntity<?> getTopFiveSubscribedTestCases() {
        return ResponseEntity.ok(testCaseService.getFiveTopSubscribedTestCases());
    }
}
