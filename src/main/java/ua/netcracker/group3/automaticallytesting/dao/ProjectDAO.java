package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.Project;
import java.util.List;

public interface ProjectDAO {

    List<Project> findAll();

}
