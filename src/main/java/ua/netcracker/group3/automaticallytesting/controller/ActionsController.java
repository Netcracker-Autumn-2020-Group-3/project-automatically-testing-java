package ua.netcracker.group3.automaticallytesting.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.service.ActionService;

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

   // @RequestMapping(value = "/library/actions",method = RequestMethod.GET)

    @GetMapping(value = "/library/actions" )
    @PreAuthorize("hasRole('ADMIN')")
    public List<Action> getPageActions(){
        return new ArrayList<>(actionService.getAllActions());
    }



}
