package com.rig.repository;

import com.rig.model.entity.User;
import com.rig.model.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<UserToken, UUID> {

    UserToken findByUser(User user);
}
