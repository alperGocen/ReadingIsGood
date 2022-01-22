package com.rig.service.impl;

import com.rig.model.entity.User;
import com.rig.model.entity.UserToken;
import com.rig.repository.TokenRepository;
import com.rig.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public void save(final UserToken userToken) {
        tokenRepository.save(userToken);
    }

    public UserToken findByUser(final User user) {
        return tokenRepository.findByUser(user);
    }
}
