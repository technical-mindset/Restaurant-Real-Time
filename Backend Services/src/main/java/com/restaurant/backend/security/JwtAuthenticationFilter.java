package com.restaurant.backend.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        logger.info("Header : {}", requestHeader);

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            // Retrieving token
            token = requestHeader.substring(7);

            try {

                username = this.jwtService.getUsernameFromToken(token);

            } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {

                logger.error("Error occurred while processing JWT token", e);

            } catch (Exception e) {

                logger.error("Unexpected error occurred", e);

            }
        }
        else {
            logger.info("Invalid Header value !!!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (username != null && authentication == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean isValidToken = this.jwtService.validateToken(token, userDetails);

            if (isValidToken) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            else {
                logger.info("Validation fails !!");
            }
        }

        filterChain.doFilter(request, response);



    }
}
