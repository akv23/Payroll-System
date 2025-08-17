package com.payroll.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Skip JWT filter for authentication endpoints
        if (path.startsWith("/api/auth/login")) {
            return true;
        }

        // Filter if no Authorization header is present
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false; // Let it go through the filter to handle missing token
        }

        return false;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header check");
            handleInvalidToken(response, "JWT token is missing or empty.");
            //filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = authHeader.substring(7);
            System.out.println("Token: " + token);
            
            // First validate the token
            if (!jwtUtil.validateToken(token)) {
                handleInvalidToken(response, "Invalid JWT token");
                return;
            }

            final String username = jwtUtil.extractUsername(token);
            final String role = jwtUtil.extractRoles(token); // Expecting a list

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Convert roles to GrantedAuthority
                GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_" + role);
                System.out.println("Roles from token: " + role);
                System.out.println("Authorities: " + authorities);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
                Collections.singletonList(authorities));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            handleInvalidToken(response, "Token has expired");
            return;
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
            // Catch all other token-related exceptions
            handleInvalidToken(response, "Invalid JWT token");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid token format: " + e.getMessage());
            handleInvalidToken(response, "Invalid token format");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleInvalidToken(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
