package ua.netcracker.group3.automaticallytesting.dto;

import lombok.Data;

@Data
public class TestCaseExecNotificationDto {
    long id;
    String name;
    String status;
}
