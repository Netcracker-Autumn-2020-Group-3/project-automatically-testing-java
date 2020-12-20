package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.ProjectDto;
import ua.netcracker.group3.automaticallytesting.dto.ProjectListPaginationDto;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

public interface ProjectDAO {

    List<Project> findAll(ProjectListPaginationDto pagination);

    Project getProjectById(Long id);

    ProjectDto getProjectDtoById(Long id);

    void update(Project project);

    Integer countProjects(ProjectListPaginationDto pagination);

    void insert(Project project);

    void updateIsArchivedField(Long projectId, boolean isArchived);
}
