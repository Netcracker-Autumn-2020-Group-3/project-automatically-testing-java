package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.CreateUpdateTestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseWithUserDto;
import ua.netcracker.group3.automaticallytesting.exception.ValidationException;
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
        testCaseService.updateTestCase(createUpdateTestCaseDto);
    }

    @PatchMapping("/{id}/follow")
    public void follow(@PathVariable("id") Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        testCaseService.addSubscriber(testCaseId, userId);
    }

    @PatchMapping("/{id}/unfollow")
    public void unfollow(@PathVariable("id") Long testCaseId){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        testCaseService.removeSubscriber(testCaseId, userId);
    }

    @PatchMapping("/{id}/archive")
    public void archiveTestCase(@PathVariable("id") Long testCaseId){
        testCaseService.archiveTestCase(testCaseId);
    }

    @PatchMapping("/{id}/unarchive")
    public void unarchiveTestCase(@PathVariable("id") Long testCaseId){
        testCaseService.unarchiveTestCase(testCaseId);
    }

    @GetMapping("/{id}/is-followed")
    public Boolean isFollowed(@PathVariable("id") Long testCaseId) {
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        return testCaseService.isFollowedByUser(testCaseId, userId);
    }

    @GetMapping("/execute/{id}")
    public void execute(@PathVariable("id") Long id) {
        TestCaseDto testCaseDto =  testCaseService.getTestCase(id);
        System.out.println("Execute testCaseDto " + testCaseDto);
        System.out.println(testCaseExecutionService.executeTestCase(testCaseDto,60L));
    }

    @GetMapping("/{projectID}/list/page")
    public List<TestCaseUpd> getPageTestCases(@PathVariable("projectID") Long projectID, Integer pageSize, Integer page, String sortOrder, String sortField,
                                                   String name) throws ValidationException {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testCaseService.getTestCases (projectID, pageable, name);
    }

    @GetMapping("/{projectID}/pages/count")
    public Integer countTestCasePages(Integer pageSize, @PathVariable("projectID") Long projectId) {
        return testCaseService.countTestCasesByProject(pageSize, projectId  );
    }

    @GetMapping("/{projectID}/list/page-upd")
    public List<TestCaseWithUserDto> getPageTestCasesWithUser(@PathVariable("projectID") Long projectID, Integer pageSize, Integer page, String sortOrder, String sortField,
                                              String name) throws ValidationException {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testCaseService.getTestCasesWithUser(projectID, pageable, name);
    }



}
