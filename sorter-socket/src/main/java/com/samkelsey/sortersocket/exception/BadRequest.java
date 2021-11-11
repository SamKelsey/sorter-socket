package com.samkelsey.sortersocket.exception;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;

public class BadRequest {

    @MessageExceptionHandler
    @SendTo("/errors")
    public String handleBadRequest(MethodArgumentNotValidException ex) {
        return ex.getMessage();
    }
}
