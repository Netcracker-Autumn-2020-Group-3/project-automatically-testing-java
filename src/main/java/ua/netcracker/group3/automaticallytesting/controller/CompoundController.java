package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDto;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/compounds")
public class CompoundController {

    private final CompoundService compoundService;

    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @GetMapping
    public ResponseEntity<?> getCompounds(@RequestParam Integer pageSize,
                                       @RequestParam Integer page,
                                       @RequestParam String search,
                                       @RequestParam String sortField) {
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        pageable.setSortField(sortField);
        pageable.setSearch(search);
        pageable.setPage((page > 0 ? page - 1 : 0) * pageSize); // Будет исправлено
        return ResponseEntity.ok(compoundService.getAllCompounds(pageable));
    }

    @GetMapping("/quantity")
    public ResponseEntity<?> getQuantityCompounds(@RequestParam String search) {
        return ResponseEntity.ok(compoundService.getQuantityCompounds(search));
    }

    @GetMapping("/{id}/actions")
    public ResponseEntity<?> getAllActionsOfCompoundByCompoundId(@PathVariable("id") long compoundId) {
        List<ActionDtoWithIdNameVoid> actions =
                compoundService.getAllCompoundActionsByCompoundId(compoundId);
        return ResponseEntity.ok(actions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompoundWithById(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(compoundService.getCompoundById(id));
    }

    @PutMapping("/{id}")
    public void archiveCompoundById(@PathVariable long id) {
        compoundService.archiveCompoundById(id);
    }

    @RequestMapping(value = "/create/check/{name}",method = RequestMethod.GET)
    public boolean checkIfNameExist(@PathVariable String name){
        return compoundService.checkIfNameExist(name);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public void createCompound(@RequestBody CompoundDto compoundDto){
        compoundService.createCompound(compoundDto);
    }

    @GetMapping("/edit/{id}")
    //@PreAuthorize("hasRole('ADMIN')" + "|| hasRole('MANAGER')" + "|| hasRole('ENGINEER')")
    public CompoundDto getCompoundById(@PathVariable long id) throws Exception{
        return compoundService.getCompoundById(id);
    }
    @PutMapping("/edit/{id}")
    //@PreAuthorize("hasRole('ADMIN')" + "|| hasRole('MANAGER')" + "|| hasRole('ENGINEER')")
    public void updateCompound(@PathVariable long id, @RequestBody Compound compound){
        compoundService.updateCompound(compound, id);
    }
}
