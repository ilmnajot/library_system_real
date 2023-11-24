package sampm.uz.library_system.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import sampm.uz.library_system.exception.AuthenticationException;

import java.util.Date;

@Component
public class JwtGenerator {

    private final static String secretKey = "SKGHBSFKFJVBKSVJBDFMVBFDMVNBDFMVBSVHJVBSDJVHSF";
    private final static long expireTime = 5 * 5 * 5 * 60 * 1000L; // 5 minutes for testing

    public String generateToken(String email) {
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            Date now = new Date();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//            if (expirationDate.after(now)) {
//                return true; // Token is valid and not expired
//            } else {
//                throw new AuthenticationException("Token has expired");
//            }
//        } catch (ExpiredJwtException e) {
//            throw new AuthenticationException("Token has expired");
//        } catch (Exception e) {
//            return false; // Token validation failed for other reasons
//
//    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new AuthenticationException("there is a problem with parsing username from token");
        }

    }


}
