package com.samkelsey.sortersocket.exception;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Objects;

@ControllerAdvice
public class BadRequest {

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendTo("/errors")
    public String handleBadRequest(MethodArgumentNotValidException ex) {
        String msg = "Bad request";

        if (Objects.nonNull(ex.getBindingResult().getFieldError().getDefaultMessage())) {
            msg = msg + ": " + ex.getBindingResult().getFieldError().getDefaultMessage();
        }

        return msg;
    }
}
