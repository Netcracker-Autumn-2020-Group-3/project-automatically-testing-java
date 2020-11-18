package ua.netcracker.group3.automaticallytesting.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.service.UserPrincipal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Service
public class AuthenticationConfig implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


        if (roles.contains("ROLE_ADMIN")){
            httpServletResponse.sendRedirect("/admin");
        }else if (roles.contains("ROLE_MANAGER")){
            httpServletResponse.sendRedirect("/manager");
        }else if (roles.contains("ROLE_ENGINEER")){
            httpServletResponse.sendRedirect("/engineer");
        }
    }
}
