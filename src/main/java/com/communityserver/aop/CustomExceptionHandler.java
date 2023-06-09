package com.communityserver.aop;

import com.communityserver.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = { AddFailedException.class })
    public ResponseEntity<Object> handleAddFailedException(AddFailedException ex, HttpServletRequest request) {
        CommonResponse commonResponse = new CommonResponse("ERR_1000", ex.getMessage(), request.getServletPath());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), HttpStatus.resolve(500));
    }

    @ExceptionHandler(value = { DeletionFailedException.class })
    public ResponseEntity<Object> handleDeletionFailedException(DeletionFailedException ex, HttpServletRequest request) {
        CommonResponse commonResponse = new CommonResponse("ERR_1001", ex.getMessage(), request.getServletPath());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), HttpStatus.resolve(500));
    }

    @ExceptionHandler(value = { DuplicateException.class })
    public ResponseEntity<Object> handleDuplicateException(DuplicateException ex, HttpServletRequest request) {
        CommonResponse commonResponse = new CommonResponse("ERR_1002", ex.getMessage(), request.getServletPath());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), HttpStatus.resolve(500));
    }

    @ExceptionHandler(value = { NotMatchingException.class })
    public ResponseEntity<Object> handleNotMatchingException(NotMatchingException ex, HttpServletRequest request) {
        CommonResponse commonResponse = new CommonResponse("ERR_1003", ex.getMessage(), request.getServletPath());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), HttpStatus.resolve(500));
    }

    @ExceptionHandler(value = { PermissionDeniedException.class })
    public ResponseEntity<Object> handlePermissionDeniedException(PermissionDeniedException ex, HttpServletRequest request) {
        CommonResponse commonResponse = new CommonResponse("ERR_1004", ex.getMessage(), request.getServletPath());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), HttpStatus.resolve(500));
    }

    @ExceptionHandler(value = { UpdateFailedException.class })
    public ResponseEntity<Object> handleUpdateFailedException(UpdateFailedException ex, HttpServletRequest request) {
        CommonResponse commonResponse = new CommonResponse("ERR_1005", ex.getMessage(), request.getServletPath());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), HttpStatus.resolve(500));
    }

}