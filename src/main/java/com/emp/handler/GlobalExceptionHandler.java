package com.emp.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice  // Indicates that this class handles exceptions globally for the application
public class GlobalExceptionHandler {

    // Handles validation exceptions for invalid method arguments (e.g., invalid input data)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation Error");  // Error type
        response.put("message", "Invalid input data");  // General error message

        // Collecting all validation errors
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())  // Adding field-specific validation messages
        );

        response.put("errors", errors);  // Detailed validation errors
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Respond with 400 Bad Request status
    }

    // Handles generic exceptions across the entire application
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");  // Error type
        response.put("message", ex.getMessage());  // Detailed exception message
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Respond with 500 Internal Server Error status
    }
}
