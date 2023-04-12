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
}
