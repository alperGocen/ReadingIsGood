package com.rig.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    Authentication login(HttpServletRequest request);
    Authentication validate(HttpServletRequest request);
}
