package com.communityserver.exception;

public class PermissionDeniedException  extends RuntimeException{
    public PermissionDeniedException(){
        super("권한 부족");
    }
}