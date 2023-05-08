package com.communityserver.exception;

public class DeletionFailedException extends RuntimeException{
    public DeletionFailedException(){
        super("삭제하지 못했습니다.");
    }
}
