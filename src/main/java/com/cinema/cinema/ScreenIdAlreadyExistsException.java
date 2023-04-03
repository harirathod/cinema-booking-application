package com.cinema.cinema;

public class ScreenIdAlreadyExistsException extends Exception {

    /**
     * Constructor for InvalidScreenParameterException.
     * @param message The message associated with this exception.
     */
    public ScreenIdAlreadyExistsException(String message) {
        super(message);
    }
}
