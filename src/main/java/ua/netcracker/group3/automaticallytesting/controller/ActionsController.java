package ua.netcracker.group3.automaticallytesting.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.service.ActionService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ActionsController {

    private ActionService actionService;

    @Autowired
    public ActionsController(ActionService actionService){
        this.actionService = actionService;
    }


    @RequestMapping(value = "/library/actions",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public List<Action> getPageActions(Integer page,Integer pageSize){
        System.out.println(page);
        System.out.println(pageSize);
        Pageable pageable = Pageable.builder().page(page).pageSize(2).build();
        return actionService.getAllActions(pageable);
    }

    @RequestMapping(value = "/library/actions/{actionName}",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public List<Action> findActionsByName(@PathVariable String actionName,Integer page,Integer pageSize){
        System.out.println(actionName);
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).build();
        return actionService.findActionsByName(actionName,pageable);
    }

    @RequestMapping(value = "/library/actions/count",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public Integer getNumberOfActions(){
        return actionService.getNumberOfActions();
    }




}
