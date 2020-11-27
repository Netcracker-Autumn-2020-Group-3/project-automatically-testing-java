package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.Project;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
  Маппинг происходит по всем пятём полям, как и у сущности Project в ER диаграмме.
  1-ый параметр - id
  2-ый параметр - name
  3-ый параметр - link
  4-ый параметр - status
  5-ый параметр - userId
*/

@Component
public class ProjectMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet rs, int i) throws SQLException {
        return new Project(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getBoolean(4),
                rs.getLong(5)
        );
    }
}
