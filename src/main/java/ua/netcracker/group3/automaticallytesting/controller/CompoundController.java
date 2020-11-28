package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

@RestController
@CrossOrigin("*")
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

}
