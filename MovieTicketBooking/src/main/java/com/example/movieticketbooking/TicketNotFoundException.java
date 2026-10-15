
package com.example.movieticketbooking;

import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public TicketNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

