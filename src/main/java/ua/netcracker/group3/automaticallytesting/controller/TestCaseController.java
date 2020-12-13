package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateUpdateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.execution.TestCaseExecutionService;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.SseService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping("/test-case")
public class TestCaseController {

    private final TestCaseService testCaseService;
    private final TestCaseExecutionService testCaseExecutionService;
    private final SseService sseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService, TestCaseExecutionService testCaseExecutionService, SseService sseService) {
        this.testCaseService = testCaseService;
        this.testCaseExecutionService = testCaseExecutionService;
        this.sseService = sseService;
    }

    @PostMapping("/create")
    public void createTestCase(@RequestBody CreateUpdateTestCaseDto createUpdateTestCaseDto) {
        log.info("Test case name: {}", createUpdateTestCaseDto.getTestCaseName());
        log.info("Varval: {}", createUpdateTestCaseDto.getVariableValues());

        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        testCaseService.createTestCase(createUpdateTestCaseDto, userId);
    }


    @GetMapping("/list")
    public List<TestCaseUpd> getAllTestCases() {
        return testCaseService.getAllTestCases();

    }



    @PostMapping("/update")
    public void update(@RequestBody CreateUpdateTestCaseDto createUpdateTestCaseDto) {
        log.info("Test case: {}", createUpdateTestCaseDto);
        testCaseService.updateTestCase(createUpdateTestCaseDto);
    }

    @GetMapping("/{id}")
    public TestCaseDto getById(@PathVariable("id") Long testCaseId) {
        return testCaseService.getTestCase(testCaseId);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable("id") Long testCaseId){
        // TODO

    }

    @GetMapping("/execute/{id}")
    public void execute(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwt) {

        TestCaseDto testCaseDto =  testCaseService.getTestCase(id);
        sseService.sendSseEventsToUi(jwt,testCaseDto);
        System.out.println("testCaseDto  " + testCaseDto);
        testCaseExecutionService.executeTestCase(testCaseDto);

    }

    @GetMapping("/list/page")
    public List<TestCaseUpd> getPageTestScenarios(Integer pageSize, Integer page, String sortOrder, String sortField,
                                                   String name) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testCaseService.getTestCases (pageable, name);
    }

    @GetMapping("/pages/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer countTestCasePages(Integer pageSize) {
        return testCaseService.countPages(pageSize);
    }
}
