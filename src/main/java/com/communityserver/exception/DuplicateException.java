package com.communityserver.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(){
        super("중복되었습니다.");
    }
}
