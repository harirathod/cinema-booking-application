package com.cinema.cinema;

/**
 * This enumeration class defines the command words available.
 * @author Hari Rathod
 * @version 2023.03.30
 */

public enum CommandWord {
    HELP("help", null), QUIT("finished", null), BOOK("book", "movie title"),
    LIST("list", "movies"), BASKET("basket", null), SAVE("save", "tickets"), UNKNOWN(null, null);

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

    /**
     * Get a list of all valid commands, formatted as 'help, book, quit'
     * @return A string that describes the valid commands.
     */
    public static String getAllCommands()
    {
        String formattedString = "";

        for(CommandWord command : CommandWord.values()) {
            if(command.getCommandString() != null) {
                formattedString += "\n" + command.getCommandString();
                if(command.hasPlaceholder()) {
                    formattedString += " <" + command.getPlaceholder() + ">";
                }
            }
        }

        return formattedString;
    }
}
