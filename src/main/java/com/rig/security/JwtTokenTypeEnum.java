package com.rig.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenTypeEnum {

    MAIL_TOKEN("MAIL_TOKEN"),
    LOGIN_TOKEN("LOGIN_TOKEN");

    private final String type;
}
