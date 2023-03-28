package com.cinema.cinema;

/**
 * This enumeration class defines the command words available.
 * @author Hari Rathod
 * @version 2023.01.31
 */

public enum CommandWord {
    HELP("help", null), QUIT("finished", null), BOOK("book", "movie title"),
    LIST("list", "movies"), BASKET("basket", null),UNKNOWN(null, null);

    private String commandString;
    private String placeholder;
    CommandWord(String commandString, String placeholder)
    {

        this.commandString = commandString;
        this.placeholder = placeholder;
    }

    /**
     * Get the command string associated with a command word.
     * @return The command string associated with a command word.
     */
    public String toString()
    {
        return commandString;
    }

    /**
     * Get the placeholder associated with a command word.
     * @return The placeholder associated with a command word.
     */
    public String getPlaceholder()
    {
        return placeholder;
    }

}
