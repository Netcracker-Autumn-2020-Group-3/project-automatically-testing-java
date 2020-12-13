package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ProjectDto;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.service.ProjectService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

@RestController
@CrossOrigin("*")
@RequestMapping("/projects")
@Slf4j
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
    public ProjectDto getProject(@PathVariable("id") Long id){
        return projectService.getProjectDtoById(id);

    }

    @PostMapping()
    public void createProject(@RequestBody Project project){
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        project.setUserId(userId);
        projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public void updateProject(@PathVariable("id") Long projectId, @RequestBody Project project){
        project.setId(projectId);
        projectService.updateProject(project);
    }

    @PatchMapping("/{id}/archive")
    public void archiveProject(@PathVariable("id") Long projectId){
        log.info("id {}" , projectId);
        projectService.archiveProject(projectId);
    }

    @PatchMapping("/{id}/unarchive")
    public void unarchiveProject(@PathVariable("id") Long projectId){
        log.info("id {}" , projectId);
        projectService.unarchiveProject(projectId);
    }


}
