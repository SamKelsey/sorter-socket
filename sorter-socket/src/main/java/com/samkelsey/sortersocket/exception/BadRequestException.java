package com.samkelsey.sortersocket.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, Throwable e) {
        super(message, e);
    }

    public BadRequestException(String message) {
        super(message);
    }

}
