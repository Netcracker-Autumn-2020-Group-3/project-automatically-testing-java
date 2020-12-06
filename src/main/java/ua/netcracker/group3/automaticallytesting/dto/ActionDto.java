package ua.netcracker.group3.automaticallytesting.dto;


import lombok.*;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActionDto {
    private Long id;
    private String name;
    private List<VariableDto> variables;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionDto actionDto = (ActionDto) o;
        return id.equals(actionDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
