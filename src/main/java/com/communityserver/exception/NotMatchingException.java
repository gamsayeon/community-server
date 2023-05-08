package com.communityserver.exception;

public class NotMatchingException extends RuntimeException {
    public NotMatchingException(){
        super("해당하는 것을 찾지 못했습니다.");
    }
}
