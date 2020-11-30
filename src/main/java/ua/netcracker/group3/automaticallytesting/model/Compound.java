package ua.netcracker.group3.automaticallytesting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compound {

    private long id;
    private String name;
    private String description;

}
