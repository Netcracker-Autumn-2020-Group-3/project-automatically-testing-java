package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;

@RestController
public class CompoundController {
    private CompoundService compoundService;

    @Autowired
    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @GetMapping("/compound/{id}")
    //@PreAuthorize("hasRole('ADMIN')" + "|| hasRole('MANAGER')" + "|| hasRole('ENGINEER')")
    public Compound editCompound(@PathVariable long id) throws Exception{
        return compoundService.getCompoundById(id);
    }
    @PutMapping("/compound/{id}")
    //@PreAuthorize("hasRole('ADMIN')" + "|| hasRole('MANAGER')" + "|| hasRole('ENGINEER')")
    public void updateCompound(@PathVariable long id, @RequestBody Compound compound){
        compoundService.updateCompound(compound, id);
    }
    @PostMapping("/compound/{id}")
    public void addActionToCompound(){

    }
}
