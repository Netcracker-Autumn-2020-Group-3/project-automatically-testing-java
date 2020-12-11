package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.service.ActionService;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.service.TestCaseService;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/test-scenario")
public class TestScenarioController {

    private final TestCaseService testCaseService;
    private final TestScenarioService testScenarioService;
    private final CompoundService compoundService;
    private final ActionService actionService;

    public TestScenarioController(TestScenarioService testScenarioService,
                                  TestCaseService testCaseService, CompoundService compoundService, ActionService actionService) {
        this.testScenarioService = testScenarioService;
        this.testCaseService = testCaseService;
        this.compoundService = compoundService;
        this.actionService = actionService;
    }

    @GetMapping("/compounds")
    public ResponseEntity<?> getAllCompoundsWithIdName() {
        List<CompoundDtoWithIdName> compounds = compoundService.getAllCompoundsWithIdName();
        return ResponseEntity.ok(compounds);
    }

    @GetMapping("/actions")
    public ResponseEntity<?> getAllActionsWithIdName() {
        List<ActionDtoWithIdName> actions = actionService.getAllActionsWithIdName();
        return ResponseEntity.ok(actions);
    }

    @PostMapping
    public ResponseEntity<?> createTestScenario(@RequestBody TestScenarioDto testScenario) {
        boolean isCreated = testScenarioService.saveTestScenario(testScenario);
        return ResponseEntity.ok(isCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTestScenario(@RequestBody TestScenario testScenario) {
        testScenarioService.updateTestScenario(testScenario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/page")

    public List<TestScenario> getPageTestScenarios(Integer pageSize, Integer page, String sortOrder, String sortField,
                                   String name) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testScenarioService.getTestScenarios(pageable, name);
    }

    @GetMapping("/list")
    public List<TestScenario> getAll() {
        return testScenarioService.getAll();
    }


    /**
     * @return actions instances of test scenario
     */
    @GetMapping("/{id}/steps")
    public List<ScenarioStepDto> getTestScenarioActions(@PathVariable("id") Long testCaseId) {

        return testCaseService.getTestScenarioStep(testCaseId);
    }

    @GetMapping("/pages/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer countUserPages(Integer pageSize) {
        return testScenarioService.countPages(pageSize);
    }


}



