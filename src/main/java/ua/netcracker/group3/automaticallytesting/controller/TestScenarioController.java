package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.ActionInstanceService;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDTO;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/test-scenario")
public class TestScenarioController {

    private TestScenarioService testScenarioService;
    private ActionInstanceService actionInstanceService;

    @Autowired
    public TestScenarioController(TestScenarioService testScenarioService, ActionInstanceService actionInstanceService) {
        this.testScenarioService = testScenarioService;
        this.actionInstanceService = actionInstanceService;
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

    @GetMapping("/list")

    public List<TestScenario> getPageTestScenarios(Integer pageSize, Integer page, String sortOrder, String sortField,
                                   String name) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return testScenarioService.getTestScenarios(pageable, name);
    }



    /**
     * @return actions instances of test scenario
     */
    @GetMapping("/{id}/steps")
    public List<ScenarioStepDto> getTestScenarioActions(@PathVariable("id") Long testCaseId) {

        return actionInstanceService.getTestScenarioStep(testCaseId);
    }
}

