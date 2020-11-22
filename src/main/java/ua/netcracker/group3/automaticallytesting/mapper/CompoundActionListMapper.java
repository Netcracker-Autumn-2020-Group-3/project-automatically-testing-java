package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.ActionComp;
import ua.netcracker.group3.automaticallytesting.model.Compound;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
@Component
public class CompoundActionListMapper implements RowMapper<Compound> {

    private ArrayList<ActionComp> getActionList(ResultSet rs) throws SQLException {
        ArrayList<ActionComp> actionList = new ArrayList<>();
        do{
            actionList.add(new ActionComp(rs.getLong("action_id")));
        } while(rs.next());
        return actionList;
    }

    @Override
    public Compound mapRow(ResultSet resultSet, int i) throws SQLException {
        return Compound.builder()
                .actionList(getActionList(resultSet))
                .build();
    }
}
