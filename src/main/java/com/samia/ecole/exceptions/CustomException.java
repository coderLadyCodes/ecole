package com.samia.ecole.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    public CustomException(String message,HttpStatus status) {
        this.message=message;
        this.timestamp= LocalDateTime.now();
        this.status=status;
    }
}
