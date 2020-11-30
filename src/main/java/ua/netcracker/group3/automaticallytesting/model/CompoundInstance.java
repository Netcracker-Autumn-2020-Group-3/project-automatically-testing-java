package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompoundInstance {

    private Long id;
    private Integer priority;
    private Compound compound;

}
