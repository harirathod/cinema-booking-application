package com.cinema.cinema;

/**
 * This enumeration class defines the command words available to the manager.
 * @author Hari Rathod
 * @version 2023.04.24
 */
public enum ManagerCommandWord implements CommandWord {
    HELP("help", null), ADD("add", "movie title"), LIST("list", "movies"),
    REMOVE("remove", null),  QUIT("finished", null),

    UNKNOWN(null, null);

    private String commandString;
    private String placeholder;

    /**
     * Initialise fields.
     * @param commandString The main part of the ManagerCommandWord.
     * @param placeholder The placeholder.
     */
    ManagerCommandWord(String commandString, String placeholder)
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
     * Check whether a ManagerCommandWord has an associated placeholder or not.
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

        for(ManagerCommandWord command : ManagerCommandWord.values()) {
            if(command.getCommandString() != null) {
                formattedString += "\n" + command.getCommandString();
                if(command.hasPlaceholder()) {
                    formattedString += " <" + command.getPlaceholder() + ">";
                }
            }
        }
        return formattedString;
    }

    /**
     * Converts the string to an appropriate command word. If the string is null or unrecognised, the ManagerCommandWord returned
     * is CommandWord.UNKNOWN.
     * @param word The string to convert to a ManagerCommandWord.
     * @return The CommandWord most appropriate to the parameter entered.
     */
    public ManagerCommandWord convertToCommandWord(String word)
    {
        if(word == null) {
            return ManagerCommandWord.UNKNOWN;
        }
        word = word.toLowerCase();
        for(ManagerCommandWord command : ManagerCommandWord.values()) {
            if(word.equals(command.getCommandString())) {
                return command;
            }
        }
        return ManagerCommandWord.UNKNOWN;
    }

    /**
     * Get the quit command.
     * @return The quit command.
     */
    public ManagerCommandWord getQuitCommand() {
        return ManagerCommandWord.QUIT;
    }
}
