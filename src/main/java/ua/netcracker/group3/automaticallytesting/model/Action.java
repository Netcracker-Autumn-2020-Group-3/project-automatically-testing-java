package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

import java.util.List;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Action {

    private Long actionId;
    private String actionName;
    private String actionDescription;
    private Boolean isVoid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(actionId, action.actionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionId);
    }
}
