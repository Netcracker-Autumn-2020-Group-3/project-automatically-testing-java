package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CompoundMapper implements RowMapper<Compound> {

    @Override
    public Compound mapRow(ResultSet rs, int i) throws SQLException {
        return new Compound(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
