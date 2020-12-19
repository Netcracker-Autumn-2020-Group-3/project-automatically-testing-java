package ua.netcracker.group3.automaticallytesting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscribedUserTestCaseDto {
    private Long id;
    private String userName;
    private String email;
    private Long testCaseId;
    private String testCaseName;
}
