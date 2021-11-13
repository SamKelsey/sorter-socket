package com.samkelsey.sortersocket.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class BadRequestException {

    Logger logger = LoggerFactory.getLogger(BadRequestException.class);

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendTo("/errors")
    public ApiException handleBadRequest(MethodArgumentNotValidException ex) {
        String exMsg = "";
        if (ex.getBindingResult() != null && ex.getBindingResult().getFieldError() != null) {
            exMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        }
        String msg = String.format("Bad request: %s", exMsg);
        logger.error(msg);

        return new ApiException(HttpStatus.BAD_REQUEST, msg);
    }
}
