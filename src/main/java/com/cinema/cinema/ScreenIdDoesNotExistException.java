package com.cinema.cinema;

public class ScreenIdDoesNotExistException extends Exception {

    /**
     * Constructor for ScreenIdDoesNotExistException.
     * @param message The message associated with this exception.
     */
    public ScreenIdDoesNotExistException(String message) {
        super(message);
    }
}
