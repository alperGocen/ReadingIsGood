package com.rig.security;


import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.ErrorMessages;
import com.rig.security.service.AuthService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final AuthService authService;
    private final ResponseHelper responseHelper;

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication;
        try {
            authentication = authService.validate((HttpServletRequest) servletRequest);
        } catch (final JwtException jwtAuthenticationException) {
            responseHelper.sendErrorResponse((HttpServletResponse) servletResponse, new BackendException(
                    ErrorType.USER_AUTHORIZATION_FAIL,
                    ErrorMessages.AUTH_FAIL));

            return;
        } catch (final UsernameNotFoundException usernameNotFoundException) {
            responseHelper.sendErrorResponse(
                    (HttpServletResponse) servletResponse,
                    new BackendException(ErrorType.USER_AUTHORIZATION_FAIL, ErrorMessages.AUTH_FAIL));
            return;
        }
        securityContext.setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
