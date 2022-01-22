package com.rig.converter;

import com.rig.config.exception.ErrorType;
import com.rig.model.RIGErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RIGErrorResponseConverter {

    public RIGErrorResponse convert(final ErrorType errorType, final String errorMessage) {
        final RIGErrorResponse errorResponse = new RIGErrorResponse();

        final String title = errorType.getTitle();

        errorResponse.setMessage(errorMessage);
        errorResponse.setCode(errorType.getCode());

        return errorResponse;
    }
}
