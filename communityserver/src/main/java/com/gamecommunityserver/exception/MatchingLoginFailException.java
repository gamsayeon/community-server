package com.gamecommunityserver.exception;

public class MatchingLoginFailException extends RuntimeException{
    public MatchingLoginFailException(String message){
        super(message);
    }
}
