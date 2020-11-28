package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/test-scenario")
public class TestScenarioController {

    private TestScenarioService testScenarioService;

    public TestScenarioController(TestScenarioService testScenarioService) {
        this.testScenarioService = testScenarioService;
    }

    @PostMapping
    public ResponseEntity<?> createTestScenario(@RequestBody TestScenario testScenario) {
        testScenarioService.saveTestScenario(testScenario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTestScenario(@RequestBody TestScenario testScenario) {
        testScenarioService.updateTestScenario(testScenario);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
