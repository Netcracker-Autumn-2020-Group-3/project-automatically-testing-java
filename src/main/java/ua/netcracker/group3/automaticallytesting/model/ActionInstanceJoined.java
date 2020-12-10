package ua.netcracker.group3.automaticallytesting.model;


import lombok.*;

import java.util.Objects;

@Data
@Builder
public class ActionInstanceJoined {
    private Long id;
    private Long testScenarioId;
    private Integer priority;
    private Action action;
    private CompoundInstance compoundInstance;
    private Variable variable;
    private Long variableValueId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionInstanceJoined that = (ActionInstanceJoined) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(testScenarioId, that.testScenarioId) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(action, that.action) &&
                Objects.equals(compoundInstance, that.compoundInstance) &&
                Objects.equals(variable, that.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testScenarioId, priority, action, compoundInstance, variable);
    }
}
