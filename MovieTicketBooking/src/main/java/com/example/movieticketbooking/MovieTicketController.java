
package com.example.movieticketbooking;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class MovieTicketController {

    private final MovieTicketService ticketService;

    public MovieTicketController(MovieTicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Book a ticket
    @PostMapping
    public ResponseEntity<MovieTicket> bookTicket(
            @Valid @RequestBody MovieTicket ticket) {

        MovieTicket booked = ticketService.bookTicket(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(booked);
    }

    // View all bookings
    @GetMapping
    public List<MovieTicket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // View ticket by ID
    @GetMapping("/{id}")
    public MovieTicket getTicketById(@PathVariable int id) {
        return ticketService.getTicketById(id);
    }

    // Update booking
    @PutMapping("/{id}")
    public MovieTicket updateTicket(
            @PathVariable int id,
            @Valid @RequestBody MovieTicket ticket) {

        return ticketService.updateTicket(id, ticket);
    }

    // Cancel booking
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable int id) {

        return ResponseEntity.ok(ticketService.cancelTicket(id));
    }

    // Handle our one custom exception
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTicketException(
            TicketNotFoundException ex) {

        return ResponseEntity.status(ex.getStatus())
            .body(Map.of("error", ex.getMessage()));
    }
}

