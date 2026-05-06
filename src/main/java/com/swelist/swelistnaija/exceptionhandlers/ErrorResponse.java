package com.swelist.swelistnaija.exceptionhandlers;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }
}
