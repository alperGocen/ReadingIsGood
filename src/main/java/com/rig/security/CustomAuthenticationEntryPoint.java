package com.rig.security;

import com.rig.config.exception.AbstractBaseException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ResponseHelper responseHelper;

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException exception) throws IOException {

        responseHelper.sendErrorResponse(
                response,
                new AbstractBaseException(ErrorType.USER_AUTHORIZATION_FAIL, ErrorMessages.AUTH_FAIL)
        );
    }
}
