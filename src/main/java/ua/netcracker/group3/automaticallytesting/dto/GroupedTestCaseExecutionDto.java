package ua.netcracker.group3.automaticallytesting.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class GroupedTestCaseExecutionDto {
    private Long testCaseId;
    private String testCaseName;
    private Integer numberOfTestCaseExecution;
}
