package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.service.ProjectService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

@RestController
@CrossOrigin("*")
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<?> projects(@RequestParam Integer pageSize,
                                      @RequestParam Integer page,
                                      @RequestParam String sortOrder,
                                      @RequestParam String sortField) {

        Pageable pageable = new Pageable(pageSize, page, sortField, sortOrder);
        pageable.setPage(
                (pageable.getPage() > 0 ? pageable.getPage() - 1 : 0) * pageable.getPageSize()); // Будет исправлено
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }

}
