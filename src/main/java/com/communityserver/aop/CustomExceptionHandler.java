package com.communityserver.aop;

import com.communityserver.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = { AddFailedException.class })
    public ResponseEntity<Object> handleAddFailedException(AddFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.resolve(501), ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    @ExceptionHandler(value = { DeletionFailedException.class })
    public ResponseEntity<Object> handleDeletionFailedException(DeletionFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.resolve(502), ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    @ExceptionHandler(value = { DuplicateException.class })
    public ResponseEntity<Object> handleDuplicateException(DuplicateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.resolve(503), ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    @ExceptionHandler(value = { NotMatchingException.class })
    public ResponseEntity<Object> handleNotMatchingException(NotMatchingException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.resolve(504), ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    @ExceptionHandler(value = { PermissionDeniedException.class })
    public ResponseEntity<Object> handlePermissionDeniedException(PermissionDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.resolve(505), ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    @ExceptionHandler(value = { UpdateFailedException.class })
    public ResponseEntity<Object> handleUpdateFailedException(UpdateFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.resolve(506), ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

}