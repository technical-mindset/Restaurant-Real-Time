package com.restaurant.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.backend.helper.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        PrintWriter writer = response.getWriter();
//        writer.println("Access Denied !! " + authException.getMessage());

        /** Mapping the Response into API-Response */
        ApiResponse apiResponse = new ApiResponse(
                "Access Denied !! " + authException.getMessage(), // message
                "",  // No additional content
                false  // success = false
        );

        // Convert ApiResponse to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, apiResponse);
        out.flush();

    }
}
