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
    public CustomException(){}

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}
