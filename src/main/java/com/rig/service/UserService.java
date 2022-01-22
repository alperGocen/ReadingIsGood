package com.rig.service;

import com.rig.model.entity.User;

public interface UserService {

    User findByEmail(String email);
    String generateToken(User user);
    void save(User user);
    boolean userExist(String email);
}
