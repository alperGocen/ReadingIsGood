package com.rig.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CorsFilter extends OncePerRequestFilter {

    private final CorsProperties corsProperties;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", corsProperties.getAllowedOrigins());
        response.setHeader("Access-Control-Allow-Methods", corsProperties.getAllowedMethods());
        response.setHeader("Access-Control-Max-Age", corsProperties.getMaxAge());
        response.setHeader("Access-Control-Allow-Headers", corsProperties.getAllowedHeaders());
        response.setHeader("Access-Control-Expose-Headers", corsProperties.getExposedHeaders());

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            filterChain.doFilter(request, response);
        }

    }
}
