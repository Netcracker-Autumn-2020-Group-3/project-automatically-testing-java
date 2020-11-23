package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ProjectDAO;
import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.service.ProjectService;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO projectDAO;

    public ProjectServiceImpl(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDAO.findAll();
    }
}
