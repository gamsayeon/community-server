package com.communityserver.exception;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(String msg){
        super(msg);
    }
}
