package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ProjectDAO;
import ua.netcracker.group3.automaticallytesting.dto.ProjectDto;
import ua.netcracker.group3.automaticallytesting.mapper.ProjectDtoMapper;
import ua.netcracker.group3.automaticallytesting.mapper.ProjectMapper;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class ProjectDAOImpl implements ProjectDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ProjectMapper mapper;
    private final ProjectDtoMapper projectDtoMapper;

    @Value("${find.project.all}")
    private String FIND_ALL;

    @Value("${get.project.by.id}")
    private String GET_PROJECT_BY_ID;

    @Value("${get.project.dto.by.id}")
    private String GET_PROJECT_DTO_BY_ID;

    @Value("${count.projects}")
    private String COUNT_PROJECTS;

    @Value("${insert.project}")
    private String INSERT;

    @Value("${update.project}")
    private String UPDATE;

    @Value("${update.archive.project}")
    private String UPDATE_IS_ARCHIVED;


    public ProjectDAOImpl(JdbcTemplate jdbcTemplate, ProjectMapper mapper,  ProjectDtoMapper projectDtoMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.projectDtoMapper = projectDtoMapper;
    }

    @Override
    public List<Project> findAll(Pageable pageable) {
        String sql = String.format(FIND_ALL,
                pageable.getSortField(),
                pageable.getSortOrder(),
                pageable.getPageSize(),
                pageable.getPage()
        );
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Project getProjectById(Long id) {
        return jdbcTemplate.queryForObject(GET_PROJECT_BY_ID, mapper, id);
    }
    @Override
    public ProjectDto getProjectDtoById(Long id) {
        return jdbcTemplate.queryForObject(GET_PROJECT_DTO_BY_ID, projectDtoMapper, id);
    }

    @Override
    public void update(Project project) {
        jdbcTemplate.update(UPDATE, project.getName(), project.getLink(), project.getId());
    }

    @Override
    public Integer countProjects() {
        return jdbcTemplate.queryForObject(COUNT_PROJECTS, Integer.class);
    }

    @Override
    public void insert(Project project) {
        jdbcTemplate.update(INSERT, project.getName(), project.getLink(), project.isArchived(), project.getUserId());
    }

    @Override
    public void updateIsArchivedField(Long projectId, boolean isArchived) {
        jdbcTemplate.update(UPDATE_IS_ARCHIVED, isArchived, projectId);
    }
}
