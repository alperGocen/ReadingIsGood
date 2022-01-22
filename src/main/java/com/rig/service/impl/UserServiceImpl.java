package com.rig.service.impl;

import com.rig.config.exception.ErrorType;
import com.rig.model.entity.User;
import com.rig.repository.UserRepository;
import com.rig.security.CustomUserDetails;
import com.rig.security.JwtTokenTypeEnum;
import com.rig.security.helper.JwtHelper;
import com.rig.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;

    @Override
    public User findByEmail(final String email) {
        final User user = userRepository.findByEmail(email);

        if (Objects.isNull(user)) {
            new UsernameNotFoundException(ErrorType.MISSING_CREDENTIALS.getCode());
        }

        return user;
    }

    @Override
    public void save(final User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean userExist(final String email) {
        final User user = userRepository.findByEmail(email);

        return Objects.nonNull(user);
    }

    @Override
    public String generateToken(final User user) {
        return jwtHelper.generateToken(user, JwtTokenTypeEnum.LOGIN_TOKEN);
    }

    public CustomUserDetails getUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            return null;
        }

        return (CustomUserDetails) authentication.getDetails();
    }
}
