package ua.netcracker.group3.automaticallytesting.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthenticationConfig implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String role = "";
        for (GrantedAuthority authority : authentication.getAuthorities()){
            role = authority.getAuthority();
        }

        if (role.equals("ROLE_ADMIN")){
            httpServletResponse.sendRedirect("/admin");
        }else if (role.equals("ROLE_MANAGER")){
            httpServletResponse.sendRedirect("/manager");
        }else if (role.equals("ROLE_ENGINEER")){
            httpServletResponse.sendRedirect("/engineer");
        }
    }
}
