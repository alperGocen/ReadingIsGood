package com.rig.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rig.config.exception.AbstractBaseException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.ErrorMessages;
import com.rig.model.RIGLoginResponse;
import com.rig.model.entity.User;
import com.rig.model.entity.UserRole;
import com.rig.security.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthService authService;
    private final JwtProperties jwtProperties;
    private final ResponseHelper responseHelper;
    public LoginFilter(final String defaultFilterProcessesUrl, final AuthService authService,
                       final JwtProperties jwtProperties, final ResponseHelper responseHelper) {
        super(defaultFilterProcessesUrl);
        this.authService = authService;
        this.jwtProperties = jwtProperties;
        this.responseHelper = responseHelper;
    }
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication authResult) throws IOException {
        final User user = (User) authResult.getDetails();
        final RIGLoginResponse loginResponse = createLoginResponse(user);
        final String token = user.getUserToken().getToken();
        response.setHeader(jwtProperties.getTokenHeader(), token);
        response.getWriter().println(new ObjectMapper().writeValueAsString(loginResponse));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                              final AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof AuthenticationCredentialsNotFoundException) {
            responseHelper.sendErrorResponse(response,
                    new AbstractBaseException(ErrorType.MISSING_CREDENTIALS, ErrorMessages.AUTH_FAIL));
        } else if (failed instanceof BadCredentialsException || failed instanceof UsernameNotFoundException) {
            responseHelper.sendErrorResponse(response, new AbstractBaseException(ErrorType.BAD_CREDENTIALS, ErrorMessages.AUTH_FAIL));
        } else {
            super.unsuccessfulAuthentication(request, response, failed);
        }
    }
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest httpServletRequest,
                                                final HttpServletResponse httpServletResponse) throws AuthenticationException {
        return authService.login(httpServletRequest);
    }

    private RIGLoginResponse createLoginResponse(final User user) {
        final RIGLoginResponse loginResponse = new RIGLoginResponse();

        loginResponse.setUserEmail(user.getEmail());
        loginResponse.setSuccess(true);

        return loginResponse;
    }
}
