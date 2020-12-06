package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
  Маппинг происходит по всем трём полям, как и у сущности Compound в ER диаграмме.
  1-ый параметр - id
  2-ый параметр - name
  3-ый параметр - description
*/

@Component
public class CompoundMapper implements RowMapper<Compound> {

    @Override
    public Compound mapRow(ResultSet rs, int i) throws SQLException {
        return new Compound(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3)
        );
    }
}
