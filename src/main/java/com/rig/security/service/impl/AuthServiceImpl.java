package com.rig.security.service.impl;

import com.rig.config.exception.ErrorType;
import com.rig.constant.Constant;
import com.rig.model.entity.User;
import com.rig.model.entity.UserToken;
import com.rig.security.CustomUserDetails;
import com.rig.security.helper.JwtHelper;
import com.rig.security.helper.UserHelper;
import com.rig.security.service.AuthService;
import com.rig.service.TokenService;
import com.rig.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserHelper userHelper;
    private final JwtHelper jwtHelper;
    private final UserService userService;

    @Override
    @Transactional
    public Authentication login(final HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String[] loginInfo = getUserNameAndPaswordFromBasicAuthentication(authorization);
        final String email = loginInfo[0];
        final String password = loginInfo[1];
        final boolean isTokenExpired;
        final UserDetails userDetails;

        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(email)) {
            throw new AuthenticationCredentialsNotFoundException(
                    ErrorType.MISSING_CREDENTIALS.getCode()
            );
        }

        try {
            userDetails = userDetailsService.loadUserByUsername(email);
        } catch (final UsernameNotFoundException usernameNotFoundException) {
            throw usernameNotFoundException;
        }

        final User user = ((CustomUserDetails) userDetails).getUser();

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            UserToken userToken = tokenService.findByUser(user);

            isTokenExpired = isTokenExpired(userToken);

            if (isTokenExpired) {
                final String newToken = userService.generateToken(user);

                if (Objects.isNull(userToken)) {
                    userToken = userHelper.fillUserToken(user, newToken);
                } else {
                    userToken.setToken(newToken);
                    userToken.setExpirationDate(jwtHelper.extractExpiration(newToken));
                }

                user.setUserToken(userToken);
            }
            user.setLastLoginDate(LocalDateTime.now());
            userService.save(user);

            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    password,
                    userDetails.getAuthorities()
            );

            authentication.setDetails(user);

            return authentication;
        } else {
            throw new BadCredentialsException(ErrorType.MISSING_CREDENTIALS.getCode());
        }
    }

    public Authentication validate(final HttpServletRequest request) {
        final String token = request.getHeader(jwtHelper.getTokenHeader());
        final String email;

        if (StringUtils.isNotEmpty(token)) {
            try {
                email = jwtHelper.extractEmail(token);
            } catch (final JwtException jwtException) {
                throw new JwtException(ErrorType.USER_AUTHORIZATION_FAIL.MISSING_CREDENTIALS.getCode());
            }

            final User user = userService.findByEmail(email);

            if (Objects.nonNull(user) && Objects.nonNull(user.getUserToken())
                    && user.getUserToken().getToken().equals(token)) {

                final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword(),
                        userDetails.getAuthorities()
                );

                authentication.setDetails(userDetails);

                return authentication;
            }
        }

        return null;
    }

    private String[] getUserNameAndPaswordFromBasicAuthentication(final String authorization) {
        final String base64Credentials = authorization.substring(Constant.BASIC.length()).trim();
        final String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

        return credentials.split(Constant.COLON, Constant.SECURITY_PARAMETER_SIZE);
    }

    private boolean isTokenExpired(final UserToken userToken) {
        boolean isTokenExpired = false;

        if (Objects.nonNull(userToken)) {
            try {
                jwtHelper.isTokenExpired(userToken.getToken());
            } catch (final ExpiredJwtException exception) {
                isTokenExpired = true;
            }
        } else {
            isTokenExpired = true;
        }

        return isTokenExpired;
    }
}
