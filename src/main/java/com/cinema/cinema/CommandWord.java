package com.cinema.cinema;

/**
 * This enumeration class defines the command words available.
 * @author Hari Rathod
 * @version 2023.03.30
 */

public enum CommandWord {
    HELP("help", null), QUIT("finished", null), BOOK("book", "movie title"),
    LIST("list", "movies"), BASKET("basket", null),UNKNOWN(null, null);

    private String commandString;
    private String placeholder;

    /**
     * Initialise fields.
     * @param commandString The main part of the CommandWord.
     * @param placeholder The placeholder.
     */
    CommandWord(String commandString, String placeholder)
    {

        this.commandString = commandString;
        this.placeholder = placeholder;
    }

    /**
     * Get the command string associated with a command word.
     * @return The command string associated with a command word.
     */
    public String getCommandString()
    {
        return commandString;
    }

    /**
     * Get the placeholder associated with a command word. Returns null
     * @return The placeholder associated with a command word.
     */
    public String getPlaceholder()
    {
        return placeholder;
    }

    /**
     * Check whether a CommandWord has an associated placeholder or not.
     * @return True if it has a placeholder, false otherwise.
     */
    public boolean hasPlaceholder()
    {
        return placeholder != null;
    }

}
