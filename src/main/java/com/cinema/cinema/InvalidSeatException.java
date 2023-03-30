package com.cinema.cinema;

public class InvalidSeatException extends Exception {

    /**
     * Constructor for InvalidSeatException.
     * @param message The message associated with this exception.
     */
    public InvalidSeatException(String message) {
        super(message);
    }
}
