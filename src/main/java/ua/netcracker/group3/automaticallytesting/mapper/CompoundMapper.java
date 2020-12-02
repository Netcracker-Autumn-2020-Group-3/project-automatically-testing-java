package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.ActionComp;
import ua.netcracker.group3.automaticallytesting.model.Compound;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class CompoundMapper implements RowMapper<Compound> {
    @Override
    public Compound mapRow(ResultSet rs, int i) throws SQLException {

        return Compound.builder()
                .compound_id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
    }
}
