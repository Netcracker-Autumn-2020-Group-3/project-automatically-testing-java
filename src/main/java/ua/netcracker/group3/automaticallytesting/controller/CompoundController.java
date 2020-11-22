package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;

/* НЕ РЕАЛИЗОВАНА ПАГИНАЦИЯ! */
/* НЕ РЕАЛИЗОВАНА ПАГИНАЦИЯ! */
/* НЕ РЕАЛИЗОВАНА ПАГИНАЦИЯ! */

@RestController
@CrossOrigin("*")
public class CompoundController {

    private CompoundService compoundService;

    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @GetMapping("/compounds")
    public ResponseEntity<?> compounds() {
        return ResponseEntity.ok(compoundService.getAllCompounds());
    }

}
