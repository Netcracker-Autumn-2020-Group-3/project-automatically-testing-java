package ua.netcracker.group3.automaticallytesting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestScenarioItemDto {
    private long id;
    private long priority;
    private String type;

    public TestScenarioItemDto(long id, long priority) {
        this.id = id;
        this.priority = priority;
    }
}
