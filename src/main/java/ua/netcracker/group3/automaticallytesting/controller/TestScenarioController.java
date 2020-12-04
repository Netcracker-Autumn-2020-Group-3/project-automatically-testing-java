package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.service.ActionService;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.ActionInstanceService;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/test-scenarios")
public class TestScenarioController {

    private final TestScenarioService testScenarioService;
    private final ActionInstanceService actionInstanceService;
    private final CompoundService compoundService;
    private final ActionService actionService;

    public TestScenarioController(TestScenarioService testScenarioService, ActionInstanceService actionInstanceService, CompoundService compoundService, ActionService actionService) {
        this.testScenarioService = testScenarioService;
        this.actionInstanceService = actionInstanceService;
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
        testScenarioService.saveTestScenario(testScenario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTestScenario(@RequestBody TestScenario testScenario) {
        testScenarioService.updateTestScenario(testScenario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<TestScenario> getAllTestScenarios() {
        return testScenarioService.getAll();
    }

    /**
     * @return actions instances of test scenario
     */
    @GetMapping("/{id}/steps")
    public List<ScenarioStepDto> getTestScenarioActions(@PathVariable("id") Long testCaseId) {

        return actionInstanceService.getTestScenarioStep(testCaseId);
    }
}

