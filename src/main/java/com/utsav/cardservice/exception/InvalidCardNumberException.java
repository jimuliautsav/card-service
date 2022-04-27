package com.utsav.cardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class to handle invalid credit card number exception
 *
 * @author UtsavJ
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCardNumberException extends Exception {

    public InvalidCardNumberException(final String errorMessage) {
        super(errorMessage);
    }
}
