package com.rsd.yaycha.exception;

import java.time.LocalDateTime;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GlobalResponseError> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), "ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalResponseError> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        return buildErrorResponse("Data Integrity Violation", "DATA_INTEGRITY_ERROR", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalResponseError> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        return buildErrorResponse("Constraint Violation Error", "CONSTRAINT_VIOLATION", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponseError> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse("Internal Server Error", "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<GlobalResponseError> buildErrorResponse(String message, String errorCode, HttpStatus status) {
        GlobalResponseError errorResponse = new GlobalResponseError(status.value(), message, errorCode, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, status);
    }
}