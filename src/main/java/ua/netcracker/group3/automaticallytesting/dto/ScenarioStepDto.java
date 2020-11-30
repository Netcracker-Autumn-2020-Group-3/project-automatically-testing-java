package ua.netcracker.group3.automaticallytesting.dto;

import lombok.*;
import ua.netcracker.group3.automaticallytesting.model.Compound;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScenarioStepDto {

    private Integer priority;
    /**
     * can be null
     */
    private Compound compound;
    /**
     * consists of only one action if compound is null
     */
    private List<ActionDto> actionDto;

}
