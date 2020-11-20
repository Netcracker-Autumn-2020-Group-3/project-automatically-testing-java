package ua.netcracker.group3.automaticallytesting.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:constants.properties")
public class Pagination {

    @Value("${page.size}")
    private int defaultPageSize;
    @Value("${page.offset}")
    private int defaultPageOffset;
    @Value("${page.user.sort.field}")
    private String defaultPageUserSortField;
    @Value("${page.user.sort.order}")
    private String defaultPageUserSortOrder;

    public Pageable replaceNullsUserPage(Pageable pageable) {
        return Pageable.builder().pageSize(pageable.getPageSize() == null ? defaultPageSize : pageable.getPageSize())
                .offset(pageable.getOffset() == null ? defaultPageOffset : pageable.getOffset())
                .sortField(pageable.getSortField() == null ? defaultPageUserSortField : pageable.getSortField())
                .sortOrder(pageable.getSortOrder() == null ? defaultPageUserSortOrder : pageable.getSortOrder())
                .build();
    }
}
