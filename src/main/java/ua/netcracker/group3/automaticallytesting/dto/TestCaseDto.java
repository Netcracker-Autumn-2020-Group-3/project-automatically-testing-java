package ua.netcracker.group3.automaticallytesting.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestCaseDto {
    private Long id;
    private String name;
    private Long userId;
    private Long projectId;
    private Long testScenarioId;
    private Long dataSetId;
    List<ScenarioStepDto> scenarioStepsWithData;
}
