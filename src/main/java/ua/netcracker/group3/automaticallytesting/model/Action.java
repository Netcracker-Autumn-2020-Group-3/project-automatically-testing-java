package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Action {

    private Long actionId;
    private String actionName;
    private String actionDescription;
    private Boolean isVoid;

}
