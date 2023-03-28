package com.cinema.cinema; /**
 * This class processes individual lines of user input. It converts the input from a String into a main.cinema.Command, and returns it.
 * @author Hari Rathod
 * @version 2023.02.05
 */

import java.util.Scanner;

public class Parser {
    private Scanner scanner;                // scanner to read input
    private CommandWordConverter commandWords;       // a list of all the command words


    /**
     * Constructor to initialise the fields.
     */
    public Parser() {
        scanner = new Scanner(System.in);
    }

    /**
     * Read the next line of input and convert the String input to a complete main.cinema.Command.
     * @return The next line, in the format of a complete main.cinema.Command.
     */
    public Command readInput()
    {
        String commandWord = null;               // first word defines the command
        String secondWord = null;               // second word defines the object (or subject) of the command

        System.out.print("> ");
        String inputLine = scanner.nextLine().toLowerCase();      // process the next line

        // split the input string into 2 parts: the command, and the subject of the command
        String[] inputWords = inputLine.trim().split(" ", 2);
        for(int i = 0; i < inputWords.length; i++) {
            inputWords[i] = inputWords[i].trim();
        }
        if(inputWords.length > 0) {             // prevent IndexOutOfBoundsException, by checking how many words have been provided
            commandWord = inputWords[0];
            if (inputWords.length > 1) {
                secondWord = inputWords[1];
            }
        }
        return new Command(CommandWordConverter.toCommandWord(commandWord), secondWord);
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
