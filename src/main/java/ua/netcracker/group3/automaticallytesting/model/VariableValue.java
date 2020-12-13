package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VariableValue {
    private Long id;
    private Long actionInstanceId;
    private Long variableId;
    private Long dataEntryId;
    private Long testScenarioId;
}
