package com.example.demo.exception;

import com.example.demo.models.ErrorResponse;
import lombok.Getter;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        // Get all field errors
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        // Get all global errors
        List<ObjectError> globalErrors = e.getBindingResult().getGlobalErrors();
        // Collect all error messages
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError globalError : globalErrors) {
            errorMessages.add(globalError.getDefaultMessage());
        }
        // Concatenate error messages
        String errorMessage = "Validation Errors: " + String.join(", ", errorMessages);

        // Return ResponseEntity with 400 Bad Request status and error message
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConnectionFailure(DataAccessResourceFailureException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database connection failure");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OneExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleOneExceptionHandler(OneExceptionHandler ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    public static class OneExceptionHandler extends RuntimeException {
        private final HttpStatus status;
        public OneExceptionHandler(String message, HttpStatus status) {
            super(message);
            this.status = status;
        }
    }
}

