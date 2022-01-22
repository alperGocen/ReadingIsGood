package com.rig.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rig.config.exception.AbstractBaseException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@AllArgsConstructor
public class ResponseHelper {

    private final ObjectMapper objectMapper;

    public void sendResponse(final HttpServletResponse response, final HttpStatus httpStatus,
                             final Object responseBody) throws IOException {

        if (!response.isCommitted()) {
            populateResponse(response, httpStatus);

            final PrintWriter writer = response.getWriter();

            objectMapper.writeValue(writer, responseBody);

            writer.flush();
        }
    }

    public void sendErrorResponse(final HttpServletResponse response,
                                 final AbstractBaseException exception) throws IOException {

        final String errorMessage = exception.getErrorMessage();

        sendResponse(response, exception.getErrorType().getHttpStatus(), errorMessage);
    }

    public void populateResponse(final HttpServletResponse response, final HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
