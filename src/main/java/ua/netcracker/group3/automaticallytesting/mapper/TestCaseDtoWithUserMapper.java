package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseWithUserDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestCaseDtoWithUserMapper implements RowMapper<TestCaseWithUserDto> {
    @Override
    public TestCaseWithUserDto mapRow(ResultSet rs, int i) throws SQLException {
        return TestCaseWithUserDto.builder()
                .testCaseUpd(TestCaseUpd.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build())
                .email(rs.getString("email"))
                .build();

    }
}
