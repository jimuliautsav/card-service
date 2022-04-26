package com.utsav.cardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCardNumberException extends Exception {

    public InvalidCardNumberException(final String errorMessage, final Throwable cause) {
        super(errorMessage, cause);
    }

    public InvalidCardNumberException(final String errorMessage) {
        super(errorMessage);
    }
}
