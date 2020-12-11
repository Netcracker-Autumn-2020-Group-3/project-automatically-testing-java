package ua.netcracker.group3.automaticallytesting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActionDtoWithIdNameVoid {
    private long id;
    private String name;
    private boolean isVoid;
}
