package com.cinema.cinema;

public class MovieDoesNotExistException extends Exception {

    /**
     * Constructor for MovieDoesNotExistException.
     * @param message The message associated with this exception.
     */
    public MovieDoesNotExistException(String message) {
        super(message);
    }
}
