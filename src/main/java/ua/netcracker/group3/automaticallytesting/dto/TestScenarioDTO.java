package ua.netcracker.group3.automaticallytesting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestScenarioDTO {
    private long id;
    private String name;
}
