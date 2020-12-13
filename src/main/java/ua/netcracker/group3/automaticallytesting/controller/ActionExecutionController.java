package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.service.ActionExecutionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ActionExecutionController {

    private final ActionExecutionService actionExecutionService;

    @Autowired
    public ActionExecutionController(ActionExecutionService actionExecutionService){
        this.actionExecutionService = actionExecutionService;
    }

    @GetMapping("/list/actions-execution/{testCaseExecutionId}")
    public List<ActionExecutionDto> getAllActionExecutions(@PathVariable Long testCaseExecutionId){
        return actionExecutionService.getAllActionExecutions(testCaseExecutionId);
    }
}
