package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.config.JwtResponse;
import ua.netcracker.group3.automaticallytesting.dto.AuthResponseDto;

@CrossOrigin(origins = "https://automatically-testing-angular.herokuapp.com")
@RestController
@Slf4j
public class AuthRestAPIs {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthRestAPIs(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Returns ResponseEntity with status OK if user is present in DB
     * Also method generate jwt token with new session
     * @param authRequest needed for getting credentials from Angular and generate token
     * @return ResponseEntity with status
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthResponseDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        log.info("Authentication: {}",authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        log.info("Generate token: {}",jwt);
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        log.info("UserDetails: {}",userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }
}




