package com.cinema.cinema;

import java.util.Scanner;

/**
 * A console / terminal view for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public class TextView implements View {
    private Scanner scanner;

    /**
     * Sets up the view.
     */
    public void start()
    {
        scanner = new Scanner(System.in);

    }

    /**
     * Get the input from the user.
     * @return The user input.
     */
    public String getInput()
    {
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Display some formatted text to the user.
     * @param text The text to be displayed.
     */
    @Override
    public void displayWithFormatting(String text)
    {
        display(getSeparator());
        display(text);
        display(getSeparator());
    }

     /**
     * Display some text to the user.
     * @param text The text to be displayed.
     */
    @Override
    public void display(String text)
    {
        System.out.println(text);
    }

    /**
     * Display an error message to the user.
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message)
    {
        display("## " + message + " ##");
    }

    /**
     * Display an error message to the user, with both a title and content / body.
     * @param title The title of the error message.
     * @param message The content of the error message to be displayed.
     */
    @Override
    public void displayError(String title, String message)
    {
        display(title + "\n\t" + message);
    }

}
