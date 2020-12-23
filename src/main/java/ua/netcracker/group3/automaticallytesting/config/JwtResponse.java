package ua.netcracker.group3.automaticallytesting.config;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtResponse {
    private String token;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
