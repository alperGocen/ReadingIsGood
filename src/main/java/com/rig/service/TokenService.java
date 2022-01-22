package com.rig.service;

import com.rig.model.entity.User;
import com.rig.model.entity.UserToken;

public interface TokenService {

    void save(UserToken userToken);

    UserToken findByUser(User user);
}
