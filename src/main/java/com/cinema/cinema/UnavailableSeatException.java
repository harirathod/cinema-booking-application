package com.cinema.cinema;

public class UnavailableSeatException extends Exception {

    /**
     * Constructor for UnavailableSeatException.
     * @param message The message associated with this exception.
     */
    public UnavailableSeatException(String message) {
        super(message);
    }
}
