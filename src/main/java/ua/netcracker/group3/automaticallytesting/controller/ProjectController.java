package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.service.ProjectService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

@RestController
@CrossOrigin("*")
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> projects(@RequestParam Integer pageSize,
                                      @RequestParam Integer page,
                                      @RequestParam String sortOrder,
                                      @RequestParam String sortField) {

        Pageable pageable = new Pageable(pageSize, page, sortField, sortOrder);
        pageable.setPage(
                (pageable.getPage() > 0 ? pageable.getPage() - 1 : 0) * pageable.getPageSize()); // Будет исправлено
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }

    @GetMapping("/pages/count")
    public Integer countUserPages(Integer pageSize) {
        return projectService.countPages(pageSize);
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable("id") Long id){
        return projectService.getProjectById(id);

    }

    @PostMapping("/create")
    public void createProject(@RequestBody Project project){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        project.setUserId(userId);
        projectService.createProject(project);
    }

    @PostMapping("/update")
    public void updateProject(@RequestBody Project project){
        // TODO
    }


}
