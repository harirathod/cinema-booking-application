package com.cinema.cinema; /**
 * This class processes individual lines of user input. It converts the input from a String into a main.cinema.Command, and returns it.
 * @author Hari Rathod
 * @version 2023.02.05
 */

import java.util.Scanner;

public class Parser {
    private Scanner scanner;                // scanner to read input

    /**
     * Constructor to initialise the fields.
     */
    public Parser() {
        scanner = new Scanner(System.in);
    }

    /**
     * Read the next line of input and return it.
     * @return The next line of input.
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
            if(command.toString() != null) {
                formattedString += "\n" + command.toString();
                if(command.getPlaceholder() != null) {
                    formattedString += " <" + command.getPlaceholder() + ">";
                }
            }
        }
        return formattedString;
    }
}
