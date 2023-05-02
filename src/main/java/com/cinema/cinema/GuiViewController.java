package com.cinema.cinema;

import javafx.application.Platform;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class GuiViewController launches the GuiView class, and provides methods for manipulating the view. It is used as
 * an interface between the Booking classes and the GuiView, so that the Booking classes do
 * not need to specify Application.launch(GuiView.class), or Platform.runLater(...)
 * If a Booking class wants to use GuiView, they must use GuiViewController as their View.
 *
 * As the GuiView JavaFX class can only be updated from the JavaFX thread, the Booking classes cannot
 * update the view without using 'Platform.runLater(...)'. To maintain a high level of abstraction in class Booking, from the View subclasses,
 * this GuiViewController is necessary.
 *
 * @author Hari Rathod
 * @version 2023.04.17
 */
public class GuiViewController implements View {
    private GuiView guiView;

    /**
     * Launches the JavaFX GUI.
     */
    @Override
    public void start()
    {
        Platform.runLater(() -> new GuiView().start());
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

    /**
     * Get the selected save file from the user. This method is thread-safe, in that it does not return
     * until the file is obtained from the GuiView, which is run on the separate JavaFX thread.
     * @return The selected save file.
     */
    @Override
    public File getSelectedSaveFile()
    {
        AtomicReference<File> file = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            file.set(guiView.getSelectedSaveFile());
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return file.get();
    }
}
