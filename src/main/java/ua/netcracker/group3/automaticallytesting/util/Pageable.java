package ua.netcracker.group3.automaticallytesting.util;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pageable {
    private Integer pageSize;
    private Integer page;
    private String sortField;
    private String sortOrder;

}
