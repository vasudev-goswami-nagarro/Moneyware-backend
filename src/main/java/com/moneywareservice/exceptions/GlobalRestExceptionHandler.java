package com.moneywareservice.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleGenericException(final CustomException ex) {
        final CustomErrorResponse errorResponse =
                new CustomErrorResponse(LocalDateTime.now(), ex.getHttpStatus(), ex.getMessage(), null, null);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }
}
