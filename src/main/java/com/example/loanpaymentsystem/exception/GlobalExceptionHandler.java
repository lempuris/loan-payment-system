package com.example.loanpaymentsystem.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(EntityNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
	        ErrorResponse error = new ErrorResponse(
	                HttpStatus.NOT_FOUND.value(),
	                ex.getMessage(),
	                LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
	        ErrorResponse error = new ErrorResponse(
	                HttpStatus.BAD_REQUEST.value(),
	                ex.getMessage(),
	                LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
	        ErrorResponse error = new ErrorResponse(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                "An unexpected error occurred",
	                LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    public static class ErrorResponse {
	        private int status;
	        private String message;
	        private LocalDateTime timestamp;

	        public ErrorResponse(int status, String message, LocalDateTime timestamp) {
	            this.status = status;
	            this.message = message;
	            this.timestamp = timestamp;
	        }

	        public int getStatus() {
	            return status;
	        }

	        public String getMessage() {
	            return message;
	        }

	        public LocalDateTime getTimestamp() {
	            return timestamp;
	        }
	    }

}
