package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Variable {
    private Long variableId;
    private String variableName;
}
