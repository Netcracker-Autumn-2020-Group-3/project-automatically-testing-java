package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ProjectDto;
import ua.netcracker.group3.automaticallytesting.dto.ProjectListPaginationDto;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.service.ProjectService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;

@RestController
@CrossOrigin(origins = "https://automatically-testing-angular.herokuapp.com")
@RequestMapping("/projects")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> projects(@RequestParam String link,
                                      @RequestParam String name,
                                      @RequestParam Boolean onlyNotArchived,
                                      @RequestParam Integer page,
                                      @RequestParam Integer pageSize,
                                      @RequestParam String sortOrder,
                                      @RequestParam String sortField) {
        ProjectListPaginationDto pagination = new ProjectListPaginationDto(
            name, link, onlyNotArchived, page, pageSize, sortOrder, sortField
        ).setPage(page);
        return ResponseEntity.ok(projectService.getAllProjects(pagination));
    }

    @GetMapping("/pages/count")
    public Integer countUserPages(@RequestParam String link,
                                  @RequestParam String name,
                                  @RequestParam Boolean onlyNotArchived,
                                  @RequestParam Integer pageSize) {
        ProjectListPaginationDto pagination = new ProjectListPaginationDto(
                name, link, onlyNotArchived
        );
        return projectService.countPages(pagination, pageSize);
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
        projectService.archiveProject(projectId);
    }

    @PatchMapping("/{id}/unarchive")
    public void unarchiveProject(@PathVariable("id") Long projectId){
        projectService.unarchiveProject(projectId);
    }


}
