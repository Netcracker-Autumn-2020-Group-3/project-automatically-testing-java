package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
  Маппинг происходит по всем 2-м полям, как и у сущности TestScenario в ER диаграмме.
  1-ый параметр - id
  2-ый параметр - name
*/

@Component
public class TestScenarioMapper implements RowMapper<TestScenario> {

    @Override
    public TestScenario mapRow(ResultSet rs, int i) throws SQLException {
        return new TestScenario(
                rs.getLong(1),
                rs.getString(2)
        );
    }
}
