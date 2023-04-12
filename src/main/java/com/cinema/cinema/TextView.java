package com.cinema.cinema;

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

    /**
     * Return a line of dashes, that can be used to separate sections of text. Use to increase readability.
     * @return A line of dashes, to separate sections of text.
     */
    private String getSeparator()
    {
        return "    ---------------------------------    ";
    }
}
