package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Variable {
    private Long id;
    private String name;
}
