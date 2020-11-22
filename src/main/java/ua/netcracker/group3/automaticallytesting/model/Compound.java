package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Compound {
    private long compound_id;
    private String name;
    private String description;
    private List<ActionComp> actionList;
}
