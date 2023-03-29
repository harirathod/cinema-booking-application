package com.cinema.cinema;

public class NoAvailableSeatException extends Exception {

    /**
     * Constructor for NoAvailableSeatException.
     * @param message The message associated with this exception.
     */
    public NoAvailableSeatException(String message) {
        super(message);
    }
}
