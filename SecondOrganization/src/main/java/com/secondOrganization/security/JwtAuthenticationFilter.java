package com.secondOrganization.security;


import com.secondOrganization.utils.CustomException;
import com.secondOrganization.utils.JwtUtil;
import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter(urlPatterns = "/api/*")
public class JwtAuthenticationFilter extends HttpFilter {

    @Inject
    private JwtUtil jwtUtil;

    @Inject
    private com.secondOrganization.repository.TokenRepository tokenRepository;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                // Check if token stored and not expired
                tokenRepository.findByTokenValue(token).orElseThrow(() -> new CustomException("Invalid token"));
                io.jsonwebtoken.Claims claims = jwtUtil.validateToken(token);
                if (jwtUtil.isExpired(claims)) {
                    tokenRepository.hardDelete(tokenRepository.findByTokenValue(token).get().getId());
                    throw new CustomException("Token expired");
                }
                // Set SecurityContext (custom wrapper)
                request.setAttribute("claims", claims);
                // Proceed
                chain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: " + e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing Authorization header");
        }
    }
}