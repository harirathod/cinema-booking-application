package com.cinema.cinema;

public class InvalidScreenParameterException extends Exception {

    /**
     * Constructor for InvalidScreenParameterException.
     * @param message The message associated with this exception.
     */
    public InvalidScreenParameterException(String message) {
        super(message);
    }
}
