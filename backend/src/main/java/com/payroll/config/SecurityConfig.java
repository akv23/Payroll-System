package com.payroll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // PasswordEncoder bean to encode passwords
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain bean to configure security settings
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()                                             // Allow public access to login endpoint
                .requestMatchers("/api/auth/register").hasRole("SUPER_ADMIN")                               // Allow only SUPER_ADMIN to register
                .requestMatchers("/api/admin/**").hasRole("SUPER_ADMIN")                                    // Allow only SUPER_ADMIN to access admin endpoints
                .requestMatchers(HttpMethod.GET, "/api/employee-data/**").hasAnyRole("ADMIN", "SUPER_ADMIN")     // Allow ADMIN and SUPER_ADMIN to access employee data endpoints
                .requestMatchers("/api/employee-data/**").hasRole("SUPER_ADMIN")                                 // Allow only SUPER_ADMIN to access employee data endpoints
                .requestMatchers("/api/salary-slip/*/pdf").hasAnyRole("ADMIN", "SUPER_ADMIN")              // Allow PDF access to both roles
                .requestMatchers(HttpMethod.GET, "/api/salary-slip/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Allow GET access to both roles
                .requestMatchers("/api/salary-slip/**").hasRole("SUPER_ADMIN")                             // Other salary slip operations for SUPER_ADMIN only
                .requestMatchers(HttpMethod.GET, "/api/payroll/**").hasAnyRole("ADMIN", "SUPER_ADMIN")      // Allow ADMIN and SUPER_ADMIN to access payroll endpoints
                .requestMatchers("/api/payroll/**").hasRole("SUPER_ADMIN")                                  // Allow SUPER_ADMIN to access payroll endpoints
                .anyRequest().authenticated()                                                               // Any other request requires authentication
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // Use stateless session management
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);          // Add JWT authentication filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }
}
