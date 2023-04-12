package com.cinema.cinema;

/**
 * A view interface for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public interface View {
    void displayWithFormatting(String message);
    void displayError(String message);
    void display(String message);

    /**
     * Return a line of dashes, that can be used to separate sections of text. Use to increase readability.
     * @return A line of dashes, to separate sections of text.
     */
    default String getSeparator()
    {
        return "    ---------------------------------    ";
    }
}
