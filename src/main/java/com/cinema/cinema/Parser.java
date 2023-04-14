package com.cinema.cinema;

/**
 * This class processes individual lines of user input.
 * @author Hari Rathod
 * @version 2023.03.30
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Parser {
    private Scanner scanner;

    /**
     * Initialise fields.
     */
    public Parser(InputStream inputStream) {
        scanner = new Scanner(inputStream);
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
    /**public String[] readInputAsArray()
    {
        String input = readInput();
        return StringSplitter.splitByPunctuation(input);
    }*/
}
