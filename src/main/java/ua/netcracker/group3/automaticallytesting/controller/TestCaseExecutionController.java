package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.execution.TestCaseExecutionService;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecutionStatus;
import ua.netcracker.group3.automaticallytesting.service.NotificationService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.SseService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseExecService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;
import ua.netcracker.group3.automaticallytesting.service.UserService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static ua.netcracker.group3.automaticallytesting.model.TestCaseExecutionStatus.FINISHED;

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

    @GetMapping("/get-all-with-failed-action-number")
    public List<TestCaseExecutionDto> getAllTestCaseExecutionWithFailedActionNumber() {
        return  testCaseExecService.getAllTestCaseExecutionWithFailedActionNumber();
    }

    @PostMapping("/execute/{testCaseId}")
    public void createTestCaseExecution(@PathVariable("testCaseId") Long testCaseId,
                                        @RequestBody String userEmail) {
        long userId = userService.getUserIdByEmail(userEmail);
        long testCaseExecutionId = testCaseExecService.createTestCaseExecution(testCaseId, userId);
        notificationService.addNotifications(testCaseId, testCaseExecutionId);

        executeTestCase(testCaseId, testCaseExecutionId);
    }

    public void executeTestCase(long testCaseId, long testCaseExecutionId) {
        TestCaseDto testCaseDto =  testCaseService.getTestCase(testCaseId);
        System.out.println(testCaseDto);
        long errorNumber;
        List<String> status = testCaseExecutionService.executeTestCase(testCaseDto, testCaseExecutionId);
        System.out.println(status);
        errorNumber = status.stream().filter(el -> el.equals("FAILED")).count();
        System.out.println(errorNumber);
        testCaseExecService.updateTestCaseExecution(FINISHED, testCaseExecutionId);
        sseService.sendRecentNotifications(testCaseDto.getTestCase().getId(), testCaseExecutionId);
    }
}


