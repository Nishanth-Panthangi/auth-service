<!-- SecurityConfig.java -->
package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Configuring Security Filter Chain");
        // Configure the HTTP security settings
        http
            .csrf().disable() // Disable CSRF protection for stateless REST APIs
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll() // Allow open access to authentication endpoints
                .anyRequest().authenticated() // All other requests require authentication
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Set session management to stateless
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("Initializing Password Encoder");
        // Use BCrypt for password encoding
        return new BCryptPasswordEncoder();
    }
}