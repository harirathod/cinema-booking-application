package com.cinema.cinema;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class processes individual lines of user input. It can be used to
 * read more than just from the terminal, as Parser constructor allows an
 * InputStream to be passed as a parameter.
 * @author Hari Rathod
 * @version 2023.03.30
 */
public class Parser {
    private Scanner scanner;

    /**
     * Initialise fields.
     */
    public Parser(InputStream inputStream)
    {
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
}
