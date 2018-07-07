package org.enricogiurin.sushibar.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionHandlerFilter extends OncePerRequestFilter {


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException | ExpiredJwtException e) {
            logger.error(e);
            StringResponse stringResponse;
            if (e instanceof ExpiredJwtException) {
                stringResponse = StringResponse.of("JWT token expired or no longer valid");
            } else {
                stringResponse = StringResponse.of("Error while parsing the JWT token");
            }
            String json = new ObjectMapper().writeValueAsString(stringResponse);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(json);
            response.flushBuffer();
        }
    }
}