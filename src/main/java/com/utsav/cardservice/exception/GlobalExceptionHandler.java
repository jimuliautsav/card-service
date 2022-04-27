package com.utsav.cardservice.exception;

import com.utsav.cardservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler
 *
 * @author UtsavJ
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidCardNumberException.class})
    public ResponseEntity<Object> handleInvalidCardNumberException(InvalidCardNumberException invalidCardNumberException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(invalidCardNumberException.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getBindingResult().getFieldError().getField() + Constants.INVALID_CREDIT_CARD_INPUT_VALUE ));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Constants.INTERNAL_SERVER_ERROR ));
    }
}
