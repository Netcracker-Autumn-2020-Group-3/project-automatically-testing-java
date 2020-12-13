package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Getter
@Data  // почитать
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TestCaseUpd {
    private Long id;
    private String name;
  /*  private Long userId;
    private Long projectId;
    private Long testScenarioId;
    private Long datasetId; */
}
