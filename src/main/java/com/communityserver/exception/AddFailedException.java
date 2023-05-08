package com.communityserver.exception;

public class AddFailedException extends RuntimeException{
    public AddFailedException() {
        super("추가 하지 못했습니다.");
    }
}
