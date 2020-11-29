package ua.netcracker.group3.automaticallytesting.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActionInstanceJoined {
    private Long id;
    private Long testScenarioId;
    private Integer priority;

    /*private Long actionId;
    private String actionName;
    private String actionDescription;*/
    private Action action;

    private CompoundInstance compoundInstance;
   /* private Long compoundId;
    private String compoundName;
    private Integer compoundPriority;
    private String compoundDescription;*/

    private Variable variable;
    /*private Long variableId;
    private String variableName;*/

}
