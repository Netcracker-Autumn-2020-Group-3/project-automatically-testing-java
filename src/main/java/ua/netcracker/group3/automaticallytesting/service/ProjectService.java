package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ProjectDto;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects(Pageable pageable);

    Project getProjectById(Long id);

    ProjectDto getProjectDtoById(Long id);

    Integer countPages(Integer pageSize);

    void createProject(Project project);

    void updateProject(Project project);

    void archiveProject(Long projectId);
}
