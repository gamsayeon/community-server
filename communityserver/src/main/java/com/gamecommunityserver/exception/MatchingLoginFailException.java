package com.gamecommunityserver.exception;

import java.util.List;

public class MatchingLoginFailException extends RuntimeException{
    public MatchingLoginFailException(String message){
        super(message);
    }
}
