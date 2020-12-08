package ua.netcracker.group3.automaticallytesting.util;

import com.sun.org.apache.regexp.internal.RE;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import sun.misc.BASE64Encoder;
import sun.security.util.SecurityConstants;
import ua.netcracker.group3.automaticallytesting.model.User;

import java.util.Base64;
import java.util.Date;


@PropertySource("classpath:constants.properties")
public class PasswordResetToken {
//    @Value("${reset.secret}")
    private String RESET_SECRET = "jf3e8aspol2c";

    private String RESET_SECRET_ENC = Base64.getEncoder().encodeToString(RESET_SECRET.getBytes());


    public String generatePasswordResetToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date((new Date()).getTime() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS512, RESET_SECRET_ENC)
                .compact();
        return token;
    }

    public String getEmailFromResetToken(String token){
        return Jwts.parser()
                .setSigningKey(RESET_SECRET_ENC)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String resolveToken(String token){
        if (token != null && token.startsWith("Bearer")){
            return token.replace("Bearer", "");
        }
        return null;
    }

    public boolean validatePasswordResetToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(RESET_SECRET_ENC).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtException("exception");
        }
    }
}
