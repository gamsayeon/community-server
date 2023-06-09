package com.communityserver.exception;

public class DeletionFailedException extends RuntimeException{
    public DeletionFailedException(String msg){
        super(msg);
    }
}
