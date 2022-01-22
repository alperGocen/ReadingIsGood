package com.rig.security.helper;

import com.rig.model.entity.User;
import com.rig.model.entity.UserToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserHelper {

    private final JwtHelper jwtHelper;

    public UserToken fillUserToken(final User user, final String token) {
        final UserToken userToken = new UserToken();

        userToken.setExpirationDate(jwtHelper.extractExpiration(token));
        userToken.setToken(token);
        userToken.setUser(user);

        if (Objects.nonNull(user.getUserToken())) {
            userToken.setRowId(user.getUserToken().getRowId());
        }

        return userToken;
    }
}
