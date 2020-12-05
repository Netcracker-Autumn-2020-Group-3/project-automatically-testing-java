package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.TestCaseServiceImpl;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;

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


    @GetMapping("/list")
    public List<TestCaseUpd> getAllTestCases() {
        return testCaseService.getAllTestCases();

    }



    @PostMapping("/update")
    public void update(@RequestBody TestCaseDto testCaseDto) {
        log.info("Test case: {}", testCaseDto);
        //TODO
    }

    @GetMapping("/{id}")
    public TestCaseDto getById(@PathVariable("id") Long testCaseId) {
        return testCaseService.getTestCase(testCaseId);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable("id") Long testCaseId){
        // TODO

    }
}
