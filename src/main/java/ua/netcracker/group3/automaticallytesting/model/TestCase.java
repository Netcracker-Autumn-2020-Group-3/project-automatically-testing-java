package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TestCase {
    private Long id;
    private String name;
    private Long userId;
    private Long projectId;
    private Long testScenarioId;
    private Long dataSetId;
}
