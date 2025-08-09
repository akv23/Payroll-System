package com.payroll.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle AdminNotFoundException
    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAdminNotFound(AdminNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Not Found : - "+ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // 404 Not Found
    }
    // handle OperationNotAllowedException
    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleOperationNotAllowed(OperationNotAllowedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad Request : - "+ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400 Bad Request
    }
    
    // handle NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Not Found : - "+ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // 404 Not Found
    }

    // Handle ExpiredJwtException
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Token has expired : - "+ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);// 401 Unauthorized
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Authentication failed : - "+ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);// 403 Forbidden
    }

    // Handle DuplicateResourceException
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateResource(DuplicateResourceException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
    }
}
