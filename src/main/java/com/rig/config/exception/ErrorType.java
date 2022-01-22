package com.rig.config.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    BOOK_NOT_EXIST("ERR0001", HttpStatus.BAD_REQUEST),
    MISSING_CREDENTIALS("ERR0002", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("ERR0003", HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS("ERR0004", HttpStatus.BAD_REQUEST),
    USER_AUTHORIZATION_FAIL("ERR0005", HttpStatus.UNAUTHORIZED),
    REGISTER_FAIL("ERR0006", HttpStatus.INTERNAL_SERVER_ERROR),
    STOCK_INSUFFICIENT("ERR0007", HttpStatus.UNAUTHORIZED),
    MINIMUM_ALLOWED_ORDER_EXCEEDED("ERR0008", HttpStatus.UNAUTHORIZED),
    CUSTOMER_NOT_FOUND("ERR0009", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus httpStatus;

    ErrorType(final String code, final HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getTitle() { return "error.title." + code; }

    public String getMessage() { return "error.message." + code; }
}
