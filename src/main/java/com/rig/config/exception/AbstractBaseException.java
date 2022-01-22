package com.rig.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AbstractBaseException extends RuntimeException {

    private final ErrorType errorType;
    private final String errorMessage;
}
