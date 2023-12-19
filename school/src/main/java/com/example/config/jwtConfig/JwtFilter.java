package com.example.config.jwtConfig;

import com.example.config.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final JwtGenerate jwtGenerate;
    @Qualifier("handlerExceptionResolver")
    @Autowired
    private  HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain){
        try {
            String requestHeader = request.getHeader("Authorization");
            if (requestHeader != null && requestHeader.startsWith("Bearer")) {
                String token = requestHeader.replace("Bearer ", "");
                boolean valid  = jwtGenerate.validateToken(token);
                if (valid) {
                    String username = jwtGenerate.getUsernameFromToken(token);
                    UserDetails userDetails = authService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                }
            }
//            filterChain.doFilter(request, response);
        } catch( ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                IllegalArgumentException | IOException | ServletException  e )
        {
            resolver.resolveException(request, response, null, e);
        }
    }
}
