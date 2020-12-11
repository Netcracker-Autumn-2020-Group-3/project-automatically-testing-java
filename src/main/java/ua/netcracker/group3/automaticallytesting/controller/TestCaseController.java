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

    @Autowired
    public TestCaseController(TestCaseService testCaseService, TestCaseExecutionService testCaseExecutionService) {
        this.testCaseService = testCaseService;
        this.testCaseExecutionService = testCaseExecutionService;
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

    @PostMapping("/follow")
    public void follow(@RequestBody Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        log.info("follow testcase: {}, userId: {}", testCaseId, userId);
        testCaseService.addSubscriber(testCaseId, userId);
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestBody Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        log.info("unfollow testcase: {}, userId: {}", testCaseId, userId);
        testCaseService.removeSubscriber(testCaseId, userId);
    }
    @GetMapping("/{id}/is-followed")
    public Boolean isFollowed(@PathVariable("id") Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        boolean answer =  testCaseService.isFollowedByUser(testCaseId, userId);
        log.info("is followed testcase: {}, userId: {}, is followed: {}", testCaseId, userId, answer);
        return answer;
    }

    @GetMapping("/execute/{id}")
    public void execute(@PathVariable("id") Long id) {
        TestCaseDto testCaseDto =  testCaseService.getTestCase(id);
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
