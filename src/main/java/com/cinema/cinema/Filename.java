package com.cinema.cinema;

public enum Filename {
    TICKET("tickets.ser"), SCREEN("screens.ser");
    private final String FILENAME;
    Filename(String filename)
    {
        FILENAME = filename;
    }

    /**
     * Get filename of enum type.
     * @return The name of the file.
     */
    public String toString()
    {
        return FILENAME;
    }

}
