package com.communityserver.exception;

public class PermissionDeniedException  extends RuntimeException{
    public PermissionDeniedException(String msg){
        super(msg);
    }
}