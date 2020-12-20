package ua.netcracker.group3.automaticallytesting.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionVariableDto;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.service.ActionService;
import ua.netcracker.group3.automaticallytesting.service.VariableService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ActionsController {

    private final ActionService actionService;
    private final VariableService variableService;

    @Autowired
    public ActionsController(ActionService actionService, VariableService variableService) {
        this.actionService = actionService;
        this.variableService = variableService;
    }


    @RequestMapping(value = "/library/actions",method = RequestMethod.GET)
    public List<Action> getPageActions(Integer page,String orderSearch,String orderSort,Integer pageSize){
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(orderSearch).sortOrder(orderSort).build();
        log.info("pageable : {}", pageable);
        return actionService.getAllActions(pageable);
    }

    @RequestMapping(value = "/library/actions/{actionName}",method = RequestMethod.GET)
    public List<Action> findActionsByName(@PathVariable String actionName,String orderSearch,String orderSort,Integer page,Integer pageSize){
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(orderSearch).sortOrder(orderSort).build();
        log.info("pageable : {}", pageable);
        return actionService.findActionsByName(actionName,pageable);
    }

    @RequestMapping(value = "/library/actions/count",method = RequestMethod.GET)
    public Integer getNumberOfActions(){
        return actionService.getNumberOfActions();
    }

    @PostMapping("create-action/{name}/{description}")
    public void createAction(@PathVariable("name") String name, @PathVariable("description") String description,
                             @RequestBody List<String> variableValues) {

        long id = actionService.createAction(name, description);
        this.variableService.createVariables(id, variableValues);
    }

    @GetMapping("/library/actions/get/all")
    public List<Action> getAllActions(){
        return actionService.getAllActions();
    }

    @GetMapping("/action/view-edit/{id}")
    public List<ActionVariableDto> getActionVariableById(@PathVariable Long id){
        return actionService.getActionVariableById(id);
    }

    @PutMapping("/action/view-edit/update/{id}")
    public void updateActionDescriptionById(@PathVariable Long id, @RequestBody Action action){
        actionService.updateActionDescription(id,action);
    }

}
