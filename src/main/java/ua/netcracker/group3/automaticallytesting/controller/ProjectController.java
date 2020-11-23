package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.service.ProjectService;

/* НЕ РЕАЛИЗОВАНА ПАГИНАЦИЯ! */
/* НЕ РЕАЛИЗОВАНА ПАГИНАЦИЯ! */
/* НЕ РЕАЛИЗОВАНА ПАГИНАЦИЯ! */

@RestController
@CrossOrigin("*")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<?> projects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

}
