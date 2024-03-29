package com.cinema.cinema;

import java.io.File;

/**
 * A view interface for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public interface View {

    /**
     * Starts the view.
     */
    void start();

    
    /**
     * Get the input from the user.
     * @return The user input.
     */
    String getInput();

    /**
     * Display some formatted text to the user.
     * @param text The text to be displayed.
     */
    void displayWithFormatting(String text);

    /**
     * Display an error message to the user.
     * @param message The error message to be displayed.
     */
    void displayError(String message);

    /**
     * Display an error message to the user, with both a title and content / body.
     * @param title The title of the error message.
     * @param message The content of the error message to be displayed.
     */
    void displayError(String title, String message);

    /**
     * Display some text to the user.
     * @param text The text to be displayed.
     */
    void display(String text);

    /**
     * Return a line of dashes, that can be used to separate sections of text. Use to increase readability.
     * @return A line of dashes, to separate sections of text.
     */
    default String getSeparator()
    {
        return "---------------------------------";
    }

    /**
     * Get a selected file from the user, which is the location they want to save to.
     * @return The file selected by the user.
     */
    File getSelectedSaveFile();
}
