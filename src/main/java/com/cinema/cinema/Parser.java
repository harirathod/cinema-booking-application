package com.cinema.cinema;

/**
 * This class processes individual lines of user input.
 * @author Hari Rathod
 * @version 2023.03.30
 */

import java.util.Scanner;

public class Parser {
    private Scanner scanner;

    /**
     * Initialise fields.
     */
    public Parser() {
        scanner = new Scanner(System.in);
    }

    /**
     * Read the next line of input and return it.
     * @return The next line of input, as a String.
     */
    public String readInput()
    {
        System.out.println("> ");
        return scanner.nextLine();
    }

    /**
     * Get a list of all valid commands, formatted as 'Commands: help, book, quit'
     * @return A string that describes the valid commands.
     */
    public String getAllCommands()
    {
        String formattedString = "Commands available:";

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
