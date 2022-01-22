package com.rig.config.exception;


public class BackendException extends AbstractBaseException {

    public BackendException(final ErrorType errorType, final String errorMessage) {
        super(errorType, errorMessage);
    }
}
