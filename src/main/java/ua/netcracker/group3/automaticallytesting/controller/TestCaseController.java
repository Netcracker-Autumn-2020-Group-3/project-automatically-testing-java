package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateUpdateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseWithUserDto;
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

    @PostMapping()
    public void createTestCase(@RequestBody CreateUpdateTestCaseDto createUpdateTestCaseDto) {
        log.info("Create test case: {}", createUpdateTestCaseDto);
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        testCaseService.createTestCase(createUpdateTestCaseDto, userId);
    }

    @GetMapping("/list")
    public List<TestCaseUpd> getAllTestCases() {
        return testCaseService.getAllTestCases();

    }
    @GetMapping("/{id}")
    public TestCaseDto getById(@PathVariable("id") Long testCaseId) {
        return testCaseService.getTestCase(testCaseId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long testCaseId, @RequestBody CreateUpdateTestCaseDto createUpdateTestCaseDto) {
        createUpdateTestCaseDto.setId(testCaseId);
        log.info("Update test case: {}", createUpdateTestCaseDto);
        testCaseService.updateTestCase(createUpdateTestCaseDto);
    }

    @PatchMapping("/{id}/follow")
    public void follow(@PathVariable("id") Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        log.info("Follow test case: {}, by userId: {}", testCaseId, userId);
        testCaseService.addSubscriber(testCaseId, userId);
    }

    @PatchMapping("/{id}/unfollow")
    public void unfollow(@PathVariable("id") Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        log.info("Unfollow test case: {}, by userId: {}", testCaseId, userId);
        testCaseService.removeSubscriber(testCaseId, userId);
    }

    @PatchMapping("/{id}/archive")
    public void archiveTestCase(@PathVariable("id") Long testCaseId){
        log.info("Archive test case with id={}" , testCaseId);
        testCaseService.archiveTestCase(testCaseId);
    }

    @PatchMapping("/{id}/unarchive")
    public void unarchiveTestCase(@PathVariable("id") Long testCaseId){
        log.info("Unarchive with id={}" , testCaseId);
        testCaseService.unarchiveTestCase(testCaseId);
    }

    @GetMapping("/{id}/is-followed")
    public Boolean isFollowed(@PathVariable("id") Long testCaseId) {
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        boolean answer = testCaseService.isFollowedByUser(testCaseId, userId);
        log.info("Test case: {}, by userId: {}, is followed: {}", testCaseId, userId, answer);
        return answer;
    }
    @GetMapping("/execute/{id}")
    public void execute(@PathVariable("id") Long id) {
        TestCaseDto testCaseDto =  testCaseService.getTestCase(id);
        System.out.println("Execute testCaseDto " + testCaseDto);
        System.out.println(testCaseExecutionService.executeTestCase(testCaseDto,60L));
    }

    @GetMapping("/{projectID}/pages/count")
    public Integer countTestCasePages(Integer pageSize, @PathVariable("projectID") Long projectId) {
        return testCaseService.countTestCasesByProject(pageSize, projectId  );
    }

    @GetMapping("/{projectID}/list/page-upd")
    public List<TestCaseWithUserDto> getPageTestCasesWithUser(@PathVariable("projectID") Long projectID, Integer pageSize, Integer page, String sortOrder, String sortField,
                                              String name) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testCaseService.getTestCasesWithUser(projectID, pageable, name);
    }



}
