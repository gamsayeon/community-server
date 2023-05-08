package com.communityserver.exception;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(){
        super("수정하지 못했습니다.");
    }
}
