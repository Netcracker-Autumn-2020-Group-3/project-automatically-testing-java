package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.service.ActionExecutionService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/list/actions-execution")
@Slf4j
public class ActionExecutionController {

    private final ActionExecutionService actionExecutionService;

    @Autowired
    public ActionExecutionController(ActionExecutionService actionExecutionService){
        this.actionExecutionService = actionExecutionService;
    }

    @GetMapping("/{testCaseExecutionId}")
    public List<ActionExecutionDto> getAllActionExecutions(@PathVariable Long testCaseExecutionId,
                                                           Integer page,String orderSearch,
                                                           String orderSort,Integer pageSize,String search){
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize)
                                              .sortField(orderSearch)
                                              .sortOrder(orderSort).search(search).build();
        log.info("pageable : {}", pageable);
        return actionExecutionService.getAllActionExecutions(testCaseExecutionId,pageable);
    }

    @GetMapping("/quantity/{testCaseExecutionId}")
    public Integer getQuantityActionsExecutions(@PathVariable Long testCaseExecutionId,String search){
        return actionExecutionService.getQuantityActionsExecutions(testCaseExecutionId,search);
    }
}
