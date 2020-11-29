package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ScenarioStepDto;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.ActionInstanceService;
import ua.netcracker.group3.automaticallytesting.service.TestScenarioService;

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
    public List<TestScenario> getAllTestScenarios(){
        return testScenarioService.getAll();
    }

    /**
     *
     * @return actions instances of test scenario
     */
    @GetMapping("/{id}/steps")
    public /*List<ScenarioStepDto>*/  String getTestScenarioActions(@PathVariable("id") Long testCaseId){

        /*return actionInstanceService.getTestScenarioStep(testCaseId);*/

        // !!! Заглушка пока в бд нет сценариев
        // !!! уберу когда будет реализовано создание сценария
        return "[{\"priority\":1,\"compound\":{\"id\":1,\"name\":\"login compound\"},\"actions\":[{\"id\":1,\"name\":\"Enter login\",\"variables\":[{\"id\":45,\"name\":\"input element\"},{\"id\":46,\"name\":\"login\"}]},{\"id\":2,\"name\":\"Enter password\",\"variables\":[{\"id\":47,\"name\":\"input element\"},{\"id\":48,\"name\":\"passsword\"}]},{\"id\":3,\"name\":\"Submit\",\"variables\":[{\"id\":49,\"name\":\"submit button\"}]}]},{\"priority\":2,\"actions\":[{\"id\":4,\"name\":\"Logout\",\"variables\":[{\"id\":50,\"name\":\"logout button\"}]}]}]";
    }
}

