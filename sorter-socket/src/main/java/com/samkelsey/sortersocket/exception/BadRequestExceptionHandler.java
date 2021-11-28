package com.samkelsey.sortersocket.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class BadRequestExceptionHandler {

    private final String BASE_MESSAGE = "Bad request: ";

    Logger logger = LoggerFactory.getLogger(BadRequestExceptionHandler.class);

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendTo("/errors")
    public ResponseEntity<String> handleBadRequest(MethodArgumentNotValidException ex) {
        String exMsg = "";
        if (ex.getBindingResult() != null && ex.getBindingResult().getFieldError() != null) {
            exMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        }
        String msg = BASE_MESSAGE + exMsg;
        logger.error(msg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @MessageExceptionHandler(BadRequestException.class)
    @SendTo("/errors")
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        String msg = BASE_MESSAGE + ex.getMessage();
        logger.error(msg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
}
