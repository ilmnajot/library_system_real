package sampm.uz.library_system.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sampm.uz.library_system.controller.UserController;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.entity.User;
import sampm.uz.library_system.exception.AuthenticationException;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {

//    @Value("${jwt.secret}")
//    private String jwtSecret;
    private final static String secretKey = "SKGHBSFKFJVBKSVJBDF,MVBFDMVNBDFMVBSVHJVBSD,JVHSF";
    private final static long expireDateWithTime = 365 * 24 * 3600 * 1000L;

    public String generateToken(String username) {
       try {
           return Jwts
                   .builder()
                   .setSubject(username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() + expireDateWithTime))
                   .signWith(SignatureAlgorithm.ES256, secretKey)
                   .compact();
       }catch (Exception e){
           throw new AuthenticationException("failed to sign in");
       }
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expirationDate = claims.getExpiration();
            Date now = new Date();

            if (expirationDate.after(now)) {
                return true; // Token is valid and not expired
            } else {
                throw new AuthenticationException("Token has expired");
            }
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("Token has expired");
        } catch (Exception e) {
            return false; // Token validation failed for other reasons
        }
    }


//    public boolean validateToken(String token) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            Date expireDate = claims.getExpiration();
//            return expireDate.after(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }


}
