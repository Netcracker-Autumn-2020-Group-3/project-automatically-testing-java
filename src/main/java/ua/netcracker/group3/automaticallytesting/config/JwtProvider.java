package ua.netcracker.group3.automaticallytesting.config;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Value("${jwt.token.expired}")
    private int jwtExpiration;

    private String JWT_SECRET = "jwtprodddjectng";

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        //Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtException("exception");
        }
    }


    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    public String resolveStringToken(String jwt){
        if (jwt != null && jwt.startsWith("Bearer ")){
            return jwt.replace("Bearer ", "");
        }
        return null;
    }



    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
