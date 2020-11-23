package ua.netcracker.group3.automaticallytesting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {


    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    /*@Autowired
    private JwtAuthTokenFilter jwtAuthTokenFilter;
*/  @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }


    @Autowired
    public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,UserDetailsServiceImpl userDetailsService){
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().
                authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }



    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();  //TODO realize Bcrypt(dont know how to implement cause authorization doesnt work with it)
    }


    /*@Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping( "/**" )
                .allowedOrigins( "*" )
                .allowedMethods( "GET", "POST", "DELETE" )
                .allowedHeaders( "*" )
                .allowCredentials( true )
                .exposedHeaders( "Authorization" )
                .maxAge( 3600 );
    }*/

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .exposedHeaders("Authorization");
    }
}
