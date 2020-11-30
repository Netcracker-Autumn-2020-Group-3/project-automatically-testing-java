package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.VariableValue;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.TestCaseServiceImpl;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping("/test-case")
public class TestCaseController {

    private final TestCaseServiceImpl testCaseService;

    @Autowired
    public TestCaseController(TestCaseServiceImpl testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping("/create")
    public void createTestCase(@RequestBody CreateTestCaseDto createTestCaseDto) {
        log.info("Test case name: {}", createTestCaseDto.getTestCaseName());
        log.info("Varval: {}", createTestCaseDto.getVariableValues());

        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        testCaseService.createTestCase(createTestCaseDto, userId);
    }
}
