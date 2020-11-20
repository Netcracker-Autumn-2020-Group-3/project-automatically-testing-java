package ua.netcracker.group3.automaticallytesting.util;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pageable {
    public enum SortOrder{
        ASC, DESC
    }
    private int pageSize;
    private int offset;
    private String sortField;
    private SortOrder sortOrder;
}
