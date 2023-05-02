package com.communityserver.aop;

import com.communityserver.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Annotation for handling exceptions in specific handler classes and/or handler methods.
 * Handler methods which are annotated with this annotation are allowed to have very flexible signatures.
 * They may have parameters of the following types, in arbitrary order:
 * An exception argument: declared as a general Exception or as a more specific exception.
 * This also serves as a mapping hint if the annotation itself does not narrow the exception types through its value().
 * You may refer to a top-level exception being propagated or to a nested cause within a wrapper exception.
 * As of 5.3, any cause level is being exposed, whereas previously only an immediate cause was considered.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<Object> handleNotContent(String msg, int code) {
        return new ResponseEntity<>(msg, HttpStatus.resolve(code));
    }

    @ExceptionHandler(MatchingUserFailException.class)
    private ResponseEntity<Object> handleDuplicateId(MatchingUserFailException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(421));
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    private ResponseEntity<Object> handleDuplicateCategory(DuplicateCategoryException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(422));
    }
    @ExceptionHandler(NotMatchCategoryIdException.class)
    private ResponseEntity<Object> handleNotCategory(NotMatchCategoryIdException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(423));
    }
    @ExceptionHandler(PostAccessDeniedException.class)
    private ResponseEntity<Object> handleNotAccess(PostAccessDeniedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(424));
    }
    @ExceptionHandler(PostNullException.class)
    private ResponseEntity<Object> postNullException(PostNullException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(425));
    }

}
