package ua.netcracker.group3.automaticallytesting.config;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtResponse {

    private String token;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    //private String type = "Bearer";
}
