package com.cinema.cinema;

public class TicketsNotFoundException extends Exception {

    /**
     * Constructor for this exception.
     * @param message The message associated with this exception.
     */
    public TicketsNotFoundException(String message) {
        super(message);
    }
}
