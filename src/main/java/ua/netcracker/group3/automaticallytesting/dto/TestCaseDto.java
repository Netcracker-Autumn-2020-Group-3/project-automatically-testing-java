package ua.netcracker.group3.automaticallytesting.dto;

import lombok.Builder;
import lombok.Data;
import ua.netcracker.group3.automaticallytesting.model.TestCase;

import java.util.List;

@Data
@Builder
public class TestCaseDto {
    private TestCase testCase;
    private String projectName;
    private String projectLink;
    private List<ScenarioStepDto> scenarioStepsWithData;
}
