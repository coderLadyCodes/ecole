package com.samia.ecole.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private int statuscode;

    public ApiResponse() {
    }

    public ApiResponse(String message, LocalDateTime timestamp, HttpStatus status, int statuscode) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
        this.statuscode = statuscode;
    }

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

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", statuscode=" + statuscode +
                '}';
    }
}
