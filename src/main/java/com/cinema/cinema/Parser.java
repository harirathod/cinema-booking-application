package com.cinema.cinema;

/**
 * This class processes individual lines of user input.
 * @author Hari Rathod
 * @version 2023.03.30
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Get the next line of input as an array of tokens.
     */
    public String[] readInputAsArray()
    {
        String input = readInput();
        return Pattern.compile("[\\p{Punct}\\s]+").split(input);
    }

    /**
     * Get a list of all valid commands, formatted as 'help, book, quit'
     * @return A string that describes the valid commands.
     */
    public String getAllCommands()
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
