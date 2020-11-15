package ua.netcracker.group3.automaticallytesting.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String role;
    private boolean isEnabled;

}
