package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.service.ProjectService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

/**
 * @author Danya Polishchuk
 */

@RestController
@CrossOrigin("*")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<?> projects(@RequestParam(value = "limit") Integer limit,
                                      @RequestParam(value = "offset") Integer offset,
                                      @RequestParam(value = "sortOrder") String sortOrder,
                                      @RequestParam(value = "sortField") String sortField) {
        Pageable pageable = Pageable.builder()
                .sortField(sortField)
                .sortOrder(sortOrder)
                .pageSize(limit)
                .page((offset > 0 ? (offset - 1) : 0) * limit) /* Будет исправлено */
                .build();
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }

}
