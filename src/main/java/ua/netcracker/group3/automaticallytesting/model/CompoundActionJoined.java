package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Data
@Builder
@ToString
public class CompoundActionJoined {
    private Action action;
    private Integer priority;
}
