package com.communityserver.exception;

public class ServiceException extends RuntimeException {

    private String message;
    private Throwable cause;
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }
}
