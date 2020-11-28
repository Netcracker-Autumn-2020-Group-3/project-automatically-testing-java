package ua.netcracker.group3.automaticallytesting.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DataEntry {
    private Long id;
    private Long data_set_id;
    private String value;
}
