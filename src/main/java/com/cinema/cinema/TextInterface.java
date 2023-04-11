package com.cinema.cinema;

import javafx.scene.text.Text;

public class TextInterface {
    public TextInterface()
    {

    }

    public void display(String msg)
    {
        System.out.println();
        System.out.println(msg);
    }

    public String getLineSeparator()
    {
        return
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
