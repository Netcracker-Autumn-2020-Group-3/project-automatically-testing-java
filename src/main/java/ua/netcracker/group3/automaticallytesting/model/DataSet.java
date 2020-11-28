package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DataSet {
    private Long id;
    private String name;
}