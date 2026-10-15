
package com.example.movieticketbooking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MovieTicketService {

    private final List<MovieTicket> tickets = new ArrayList<>();

    // Book a movie ticket
    public MovieTicket bookTicket(MovieTicket ticket) {

        for (MovieTicket existing : tickets) {

            if (existing.getTicketId() == ticket.getTicketId()) {
                throw new TicketNotFoundException(
                    "Ticket ID already exists: " + ticket.getTicketId(),
                    HttpStatus.CONFLICT);
            }

            if (existing.getMovieName().equalsIgnoreCase(
                    ticket.getMovieName())
                    && existing.getSeatNumber().equalsIgnoreCase(
                    ticket.getSeatNumber())) {

                throw new TicketNotFoundException(
                    "Seat " + ticket.getSeatNumber()
                    + " is already booked for "
                    + ticket.getMovieName(),
                    HttpStatus.CONFLICT);
            }
        }

        tickets.add(ticket);
        return ticket;
    }

    // View all bookings
    public List<MovieTicket> getAllTickets() {
        return tickets;
    }

    // Find booking by ticket ID
    public MovieTicket getTicketById(int id) {

        return tickets.stream()
            .filter(t -> t.getTicketId() == id)
            .findFirst()
            .orElseThrow(() -> new TicketNotFoundException(
                "Ticket not found with ID: " + id,
                HttpStatus.NOT_FOUND));
    }

    // Update booking
    public MovieTicket updateTicket(int id, MovieTicket updatedTicket) {

        for (int i = 0; i < tickets.size(); i++) {

            MovieTicket existing = tickets.get(i);

            if (existing.getTicketId() == id) {

                // Check whether another ticket has the same movie and seat
                for (MovieTicket other : tickets) {
                    if (other.getTicketId() != id
                            && other.getMovieName().equalsIgnoreCase(
                                    updatedTicket.getMovieName())
                            && other.getSeatNumber().equalsIgnoreCase(
                                    updatedTicket.getSeatNumber())) {

                        throw new TicketNotFoundException(
                            "Seat " + updatedTicket.getSeatNumber()
                            + " is already booked for "
                            + updatedTicket.getMovieName(),
                            HttpStatus.CONFLICT);
                    }
                }

                updatedTicket.setTicketId(id);
                tickets.set(i, updatedTicket);
                return updatedTicket;
            }
        }

        throw new TicketNotFoundException(
            "Cannot update. Ticket not found with ID: " + id,
            HttpStatus.NOT_FOUND);
    }

    // Cancel booking
    public String cancelTicket(int id) {

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).getTicketId() == id) {
                tickets.remove(i);
                return "Ticket cancelled successfully. ID: " + id;
            }
        }

        throw new TicketNotFoundException(
            "Cannot cancel. Ticket not found with ID: " + id,
            HttpStatus.NOT_FOUND);
    }
}
