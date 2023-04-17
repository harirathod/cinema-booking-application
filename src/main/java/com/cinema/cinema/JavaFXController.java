package com.cinema.cinema;

import javafx.application.Application;
import javafx.application.Platform;

/**
 * Class JavaFXController launches the GuiView class, and provides methods for manipulating the view. It is used as
 * an interface between the main controlling class 'CustomerBooking' and the 'GuiView', so that the 'CustomerBooking' does
 * not need to specify Application.launch(GuiView.class).
 *
 * @author hari_rathod
 * @version 2023.04.17
 */
public class JavaFXController implements View {
    private GuiView guiView;

    /**
     * Launches the JavaFX GUI.
     */
    @Override
    public void start()
    {
        new Thread(() -> {
            Application.launch(GuiView.class);
        }).start();
        guiView = GuiView.getInstance();
    }

    /**
     * Get the input from the user.
     * @return The user input.
     */
    @Override
    public String getInput() {
        return guiView.getInput();
    }

    /**
     * Display some formatted text to the user.
     * @param text The text to be displayed.
     */
    @Override
    public void displayWithFormatting(String text)
    {
        Platform.runLater(() -> guiView.displayWithFormatting(text));
    }

    /**
     * Display an error message to the user.
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message)
    {
        Platform.runLater(() -> guiView.displayError(message));
    }

    /**
     * Display an error message to the user, with both a title and content / body.
     * @param title The title of the error message.
     * @param message The content of the error message to be displayed.
     */
    @Override
    public void displayError(String title, String message)
    {
        Platform.runLater(() -> guiView.displayError(title, message));
    }

    /**
     * Display some text to the user.
     * @param text The text to be displayed.
     */
    @Override
    public void display(String text)
    {
        Platform.runLater(() -> guiView.display(text));
    }


}
