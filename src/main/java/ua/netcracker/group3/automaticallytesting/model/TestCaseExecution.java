package ua.netcracker.group3.automaticallytesting.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
public class TestCaseExecution {
    long id;
    long testCaseId;
    String status;
    //Timestamp startDateTime;
    String startDateTime;
    //Timestamp endDateTime;
    String endDateTime;
    long userId;
}