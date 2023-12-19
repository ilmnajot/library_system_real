package com.example.config;

import com.example.config.jwtConfig.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final String[] WHITE_LINE = new String[]{
//            "/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/swagger-ui.html",
            "/api/v1/user/login",
            "/v2/api-docs/**",
            "swagger-ui.html, /**/webjars/**, /**/swagger-resources, /**/swagger-resources/**, /**/v2/api-docs, /**/configuration/ui, /**/configuration/security, /**/swagger-ui/**, /**/v3/api-docs/"
    };

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOriginPatterns(List.of("*"));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setAllowCredentials(true);
                    return configuration;
                })
                .and()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LINE)
                .permitAll()
                .anyRequest()
                .authenticated()
                            .and()
                            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
