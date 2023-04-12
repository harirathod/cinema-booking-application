package com.cinema.cinema;

/**
 * A console / terminal view for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public class TextView implements View {

    /**
     * Constructor.
     */
    public TextView()
    {
        // Nothing to do here.
    }

    /**
     * Display some text to the user.
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
     * Display some formatted text to the user.
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
}
