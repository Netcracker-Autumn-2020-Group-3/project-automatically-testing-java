package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecutionDto;
import ua.netcracker.group3.automaticallytesting.execution.TestCaseExecutionService;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecutionStatus;
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


    public TestCaseExecutionController(TestCaseExecService testCaseExecService,TestCaseService testCaseService,
                                       TestCaseExecutionService testCaseExecutionService, UserService userService) {
        this.testCaseExecService = testCaseExecService;
        this.testCaseService = testCaseService;
        this.testCaseExecutionService = testCaseExecutionService;
        this.userService = userService;
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
        executeTestCase(testCaseId, testCaseExecutionId);
    }

    public void executeTestCase(long testCaseId, long testCaseExecutionId) {

        //TestCaseDto testCaseDto =  testCaseService.getTestCase(testCaseId);
        //System.out.println(testCaseExecutionService.executeTestCase(testCaseDto));

        long errorNumber;
        List status = test();
        errorNumber = status.stream().filter(el -> el == Status.FAILED).count();
        System.out.println(errorNumber);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished");
        testCaseExecService.updateTestCaseExecution(FINISHED, testCaseExecutionId);
    }

    public List<Enum> test() {
        List<Enum> enumList = new ArrayList<Enum>();
        enumList.add(Status.PASSED);
        enumList.add(Status.PASSED);
        enumList.add(Status.FAILED);
        enumList.add(Status.FAILED);
        enumList.add(Status.FAILED);
        return enumList;
    }
}

enum Status {
    PASSED, FAILED
}
