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

    @PostMapping()
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
    @GetMapping("/{id}")
    public TestCaseDto getById(@PathVariable("id") Long testCaseId) {
        return testCaseService.getTestCase(testCaseId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long testCaseId, @RequestBody CreateUpdateTestCaseDto createUpdateTestCaseDto) {
        createUpdateTestCaseDto.setId(testCaseId);
        log.info("Test case: {}", createUpdateTestCaseDto);
        testCaseService.updateTestCase(createUpdateTestCaseDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long testCaseId){
        // TODO
    }

    @PatchMapping("/{id}/follow")
    public void follow(@PathVariable("id") Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        log.info("follow testcase: {}, userId: {}", testCaseId, userId);
        testCaseService.addSubscriber(testCaseId, userId);
    }
    @PatchMapping("/{id}/unfollow")
    public void unfollow(@PathVariable("id") Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        log.info("unfollow testcase: {}, userId: {}", testCaseId, userId);
        testCaseService.removeSubscriber(testCaseId, userId);
    }

    @GetMapping("/{id}/is-followed")
    public Boolean isFollowed(@PathVariable("id") Long testCaseId) {
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        boolean answer = testCaseService.isFollowedByUser(testCaseId, userId);
        log.info("is followed testcase: {}, userId: {}, is followed: {}", testCaseId, userId, answer);
        return answer;
    }
    @GetMapping("/execute/{id}")
    public void execute(@PathVariable("id") Long id) {

        TestCaseDto testCaseDto =  testCaseService.getTestCase(id);
        System.out.println("testCaseDto  " + testCaseDto);
        System.out.println(testCaseExecutionService.executeTestCase(testCaseDto,60L));
    }

    @GetMapping("/{projectID}/list/page")
    public List<TestCaseUpd> getPageTestScenarios(@PathVariable("projectID") Long projectID, Integer pageSize, Integer page, String sortOrder, String sortField,
                                                   String name) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testCaseService.getTestCases (projectID, pageable, name);
    }

    @GetMapping("/{projectID}/pages/count")
    //@PreAuthorize("hasRole('ADMIN')")
    public Integer countTestCasePages(Integer pageSize, @PathVariable("projectID") Long projectId) {
        return testCaseService.countTestCasesByProject(pageSize, projectId  );
    }


}
