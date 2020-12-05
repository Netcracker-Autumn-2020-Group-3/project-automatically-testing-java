package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/compounds")
public class CompoundController {

    private CompoundService compoundService;

    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @GetMapping
    public ResponseEntity<?> compounds(@RequestParam Integer pageSize,
                                       @RequestParam Integer page,
                                       @RequestParam String sortOrder,
                                       @RequestParam String sortField) {

        Pageable pageable = new Pageable(pageSize, page, sortField, sortOrder);
        pageable.setPage(
                (pageable.getPage() > 0 ? pageable.getPage() - 1 : 0) * pageable.getPageSize()); // Будет исправлено
        return ResponseEntity.ok(compoundService.getAllCompounds(pageable));
    }

    @RequestMapping(value = "/create/check/{name}",method = RequestMethod.GET)
    public boolean checkIfNameExist(@PathVariable String name){
        return compoundService.checkIfNameExist(name);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Integer createCompound(@RequestBody Compound compound){
        return compoundService.createCompound(compound);
    }

    @RequestMapping(value = "/create/actions",method = RequestMethod.PUT)
    public String createCompoundActions(@RequestBody List<CompoundAction> compoundActions){
        compoundService.createCompoundActions(compoundActions);
        return "ok";
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public Compound getCompoundById(@PathVariable Long id){
        return compoundService.getCompoundById(id);
    }

    @RequestMapping(value = "/get/actions/{id}",method = RequestMethod.GET)
    public List<Action> getCompoundActionsByCompoundId(@PathVariable Integer id){
        System.out.println(compoundService.getCompoundActions(id).toString());
        return null;
    }



}
