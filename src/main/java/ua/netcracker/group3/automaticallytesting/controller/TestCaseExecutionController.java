package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.execution.TestCaseExecutionService;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.service.NotificationService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.SseService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;
import ua.netcracker.group3.automaticallytesting.service.UserService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

import static ua.netcracker.group3.automaticallytesting.model.TestCaseExecutionStatus.FINISHED;

@Slf4j
@RestController
@RequestMapping("/test-case-execution")
public class TestCaseExecutionController {
    private final TestCaseExecService testCaseExecService;
    private final TestCaseService testCaseService;
    private final TestCaseExecutionService testCaseExecutionService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final SseService sseService;


    public TestCaseExecutionController(TestCaseExecService testCaseExecService,TestCaseService testCaseService,
                                       TestCaseExecutionService testCaseExecutionService, UserService userService,
                                       NotificationService notificationService, SseService sseService) {
        this.testCaseExecService = testCaseExecService;
        this.testCaseService = testCaseService;
        this.testCaseExecutionService = testCaseExecutionService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.sseService = sseService;
    }

    @GetMapping("/get-all")
    public List<TestCaseExecution> getAllTestCaseExecutions() {
        return testCaseExecService.getAllTestCaseExecutions();
    }

    @GetMapping("/all")
    public List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber(long limit,  long offset, String orderBy, String orderByClause,
                                                                                        String testCaseName, String projectName, String status) {
        Pageable pageable = Pageable.builder().page((int) offset).pageSize((int) limit).sortOrder(orderByClause).sortField(orderBy).build();
        //actions execution status:  all - get all actions executions, passed - only passed, failed
        log.info("test case execution list: {}, search test case name: {}, search project name: {}, actions execution status: {}", pageable, testCaseName, projectName, status);
        return  testCaseExecService.getAllTestCaseExecutionWithFailedActionNumber(limit, offset, orderBy, orderByClause, testCaseName, projectName, status);
    }

    @GetMapping("/count/{testCaseName}/{projectName}/{status}")
    public Integer countTestCaseExecutions(@PathVariable("testCaseName") String testCaseName, @PathVariable("projectName") String projectName,
                                           @PathVariable("status") String status) {
        return testCaseExecService.countTestCaseExecutions(testCaseName, projectName, status);
    }

    @PostMapping("/execute/{testCaseId}")
    public void createTestCaseExecution(@PathVariable("testCaseId") Long testCaseId,
                                        @RequestBody String userEmail) {
        long userId = userService.getUserIdByEmail(userEmail);
        long testCaseExecutionId = testCaseExecService.createTestCaseExecution(testCaseId, userId);
        notificationService.addNotifications(testCaseId, testCaseExecutionId);
        executeTestCase(testCaseId, testCaseExecutionId);
    }

    private void executeTestCase(long testCaseId, long testCaseExecutionId) {
        TestCaseDto testCaseDto =  testCaseService.getTestCase(testCaseId);
        List<String> status = testCaseExecutionService.executeTestCase(testCaseDto, testCaseExecutionId);
        long errorNumber = status.stream().filter(el -> el.equals("FAILED")).count();
        testCaseExecService.updateTestCaseExecution(String.valueOf(FINISHED), testCaseExecutionId);
        sseService.sendRecentNotifications(testCaseDto.getTestCase().getId(), testCaseExecutionId);
    }
}


