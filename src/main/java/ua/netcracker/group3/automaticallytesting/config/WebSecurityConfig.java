package ua.netcracker.group3.automaticallytesting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ua.netcracker.group3.automaticallytesting.entity.Role;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource dataSource;

    private AuthenticationSuccessHandler authenticationSuccessHandler;


    @Autowired
    public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler){
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email,password,is_enabled from \"user\" where email=?")
                .authoritiesByUsernameQuery("select email, role from \"user\" where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasRole(Role.ADMIN.name())
                .antMatchers("/manager").hasAnyRole(Role.MANAGER.name(),Role.ADMIN.name())
                .antMatchers("/engineer").hasAnyRole(Role.ENGINEER.name(),Role.ADMIN.name())
                .and()
                .formLogin().successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .and()
                .csrf().disable();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }
}
