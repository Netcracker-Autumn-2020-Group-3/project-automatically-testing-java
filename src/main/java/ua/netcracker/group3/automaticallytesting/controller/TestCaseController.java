package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateTestCaseDto;

import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;

import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;

import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.model.VariableValue;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.TestCaseServiceImpl;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;
import ua.netcracker.group3.automaticallytesting.testcaseexec.TestCaseExecutionService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping("/test-case")
public class TestCaseController {

    private final TestCaseServiceImpl testCaseService;
    private final TestCaseExecutionService testCaseExecutionService;

    @Autowired
    public TestCaseController(TestCaseServiceImpl testCaseService,  TestCaseExecutionService testCaseExecutionService) {
        this.testCaseService = testCaseService;
        this.testCaseExecutionService = testCaseExecutionService;
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
        log.info("Test case id: {}", testCaseId);
        return testCaseService.getTestCase(testCaseId);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable("id") Long testCaseId){
        // TODO

    }

    @GetMapping("/execute/{id}")
    public void execute(@PathVariable("id") Long id) {
        TestCaseDto testCaseDto =  testCaseService.getTestCase(id);
        System.out.println("testCaseDto  " + testCaseDto);
        testCaseExecutionService.executeTestCase(testCaseDto , "https://github.com/");

    }

    @GetMapping("/list/page")
    public List<TestCaseUpd> getPageTestScenarios(Integer pageSize, Integer page, String sortOrder, String sortField,
                                                   String name) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testCaseService.getTestCases (pageable, name);
    }

    @GetMapping("/pages/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer countUserPages(Integer pageSize) {
        return testCaseService.countPages(pageSize);
    }
}
