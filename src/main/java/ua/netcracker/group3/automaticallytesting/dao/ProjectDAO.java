package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.Project;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

/**
 * @author Danya Polishchuk
 */

public interface ProjectDAO {

    List<Project> findAll(Pageable pageable);

}