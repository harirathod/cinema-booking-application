package com.cinema.cinema;

import java.util.Arrays;

/**
 * This enumeration class defines the booking types available in the cinema.
 * @author Hari Rathod
 * @version 2023.04.24
 */
public enum BookingType {
    CUSTOMER(CommandWord.BOOK, CommandWord.BASKET, CommandWord.SAVE),
    MANAGER(CommandWord.ADD, CommandWord.REMOVE);

    private CommandWord[] commandWords;

    /**
     * Initialise fields.
     * @param commands The commands that are available for the booking type.
     */
    BookingType(CommandWord... commands) {
        this.commandWords = new CommandWord[commands.length + 4];

        // Add the default commands that are available for both customers and managers.
        this.commandWords[0] = CommandWord.HELP;
        this.commandWords[1] = CommandWord.LIST;
        this.commandWords[2] = CommandWord.QUIT;
        this.commandWords[3] = CommandWord.UNKNOWN;

        // Add the commands that are specific to the booking type.
        for (int i = 4; i < commandWords.length; i++) {
            this.commandWords[i] = commands[i - 4];

        }
    }

    /**
     * Get the commands that are available for the booking type.
     * @return The commands that are available for the booking type.
     */
    public CommandWord[] getCommands() {
        return commandWords;
    }

    /**
     * Get a formatted string of all valid commands specific to the booking type, formatted as 'help, book, quit'
     * @return A string that describes the valid commands.
     */
    public String getAllCommandsAsString()
    {
        String formattedString = "";

        for (CommandWord command : getCommands()) {
            if (command.getCommandString() != null) {
                formattedString += "\n" + command.getCommandString();
                if (command.hasPlaceholder()) {
                    // Display a placeholder next to the command, to provide context to the user as to what the command does.
                    formattedString += " <" + command.getPlaceholder() + ">";
                }
            }
        }
        return formattedString;
    }
}
