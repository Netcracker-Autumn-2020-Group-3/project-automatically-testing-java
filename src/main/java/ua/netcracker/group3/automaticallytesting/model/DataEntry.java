package ua.netcracker.group3.automaticallytesting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataEntry {
    private Long id;
    private Long data_set_id;
    private String value;
}
