package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ProjectDAO;
import ua.netcracker.group3.automaticallytesting.mapper.ProjectMapper;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

@Repository
@PropertySource("classpath:queries/postgres.properties")
public class ProjectDAOImpl implements ProjectDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ProjectMapper mapper;

    @Value("${find.project.all}")
    private String FIND_ALL;

    @Value("${get.project.by.id}")
    private String GET_PROJECT_BY_ID;

    @Value("${count.projects}")
    private String COUNT_PROJECTS;

    @Value("${insert.project}")
    private String INSERT;

    public ProjectDAOImpl(JdbcTemplate jdbcTemplate, ProjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
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
    public Integer countProjects() {
        return jdbcTemplate.queryForObject(COUNT_PROJECTS, Integer.class);
    }

    @Override
    public void insert(Project project) {
        jdbcTemplate.update(INSERT, project.getName(), project.getLink(), project.isArchived(), project.getUserId());
    }
}
