package com.moneywareservice.exceptions;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException {

    String message;
    HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        setHttpStatus(httpStatus);
        setMessage(message);
    }
}
