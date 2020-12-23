package ua.netcracker.group3.automaticallytesting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCountDto {
    private Long userCount;
    private Long adminCount;
    private Long engineerCount;
    private Long managerCount;

}
