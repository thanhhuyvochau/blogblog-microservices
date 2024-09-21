package org.blogblog.userservice.config.security;

import org.blogblog.userservice.config.security.jwt.JwtFilter;
import org.blogblog.userservice.config.security.jwt.JwtUserDetailServiceImpl;
import org.blogblog.userservice.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final JwtUserDetailServiceImpl jwtUserDetailService;

    public SecurityConfig(JwtFilter jwtFilter, JwtUserDetailServiceImpl jwtUserDetailService) {
        this.jwtFilter = jwtFilter;
        this.jwtUserDetailService = jwtUserDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoding
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    List<EscapeUrlConfig.EscapeUrl> escapeUrls = EscapeUrlConfig.getEscapeUrls();
                    for (EscapeUrlConfig.EscapeUrl escapeUrl : escapeUrls) {
                        registry.requestMatchers(escapeUrl.getUrl()).permitAll();
                    }
                })
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(jwtUserDetailService);
        return http.build();
    }

}