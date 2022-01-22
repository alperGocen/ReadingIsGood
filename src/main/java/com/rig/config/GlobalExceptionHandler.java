package com.rig.config;

import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.ErrorMessages;
import com.rig.converter.RIGErrorResponseConverter;
import com.rig.model.RIGErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final RIGErrorResponseConverter errorResponseConverter;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BackendException.class})
    @ResponseBody
    protected RIGErrorResponse handleBackendException(final BackendException exception) {
        final RIGErrorResponse errorResponse = errorResponseConverter.convert(
                exception.getErrorType(),
                exception.getErrorMessage());

        log.error("Unexpected Exception. Error code is: {}, Error message is: {}, StackTrace is: {} ",
                errorResponse.getCode(),
                exception.getErrorMessage(),
                exception.getStackTrace());

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ObjectOptimisticLockingFailureException.class})
    @ResponseBody
    protected RIGErrorResponse handleOptimisticLockException(final ObjectOptimisticLockingFailureException exception) {
        final RIGErrorResponse errorResponse = errorResponseConverter.convert(
                ErrorType.STOCK_INSUFFICIENT,
                ErrorMessages.STOCK_CHANGED);

        return errorResponse;
    }
}
