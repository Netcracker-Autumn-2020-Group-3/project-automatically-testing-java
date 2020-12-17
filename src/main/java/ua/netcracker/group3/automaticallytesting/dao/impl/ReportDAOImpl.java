package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.ReportDAO;
import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;
import ua.netcracker.group3.automaticallytesting.mapper.SubscribedUserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReportDAOImpl implements ReportDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SubscribedUserMapper subscribedUserMapper;

    @Value("${get.subscribed.users}")
    private String GET_SUBSCRIBED_USERS;

    @Autowired
    public ReportDAOImpl(JdbcTemplate jdbcTemplate,SubscribedUserMapper subscribedUserMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.subscribedUserMapper = subscribedUserMapper;
    }


    @Override
    public List<SubscribedUserTestCaseDto> getSubscribedUsers(Long testCaseExecutionId) {
        return jdbcTemplate.queryForStream(GET_SUBSCRIBED_USERS,subscribedUserMapper,testCaseExecutionId).collect(Collectors.toList());
    }
}
