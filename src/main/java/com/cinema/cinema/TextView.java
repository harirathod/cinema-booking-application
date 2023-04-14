package com.cinema.cinema;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * A console / terminal view for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public class TextView implements View {
    private Scanner scanner;
    private boolean waitingForInput;
    private BlockingQueue<String> blockingQueue;

    /**
     * Sets up the view.
     */
    public void start()
    {
        waitingForInput = false;
        blockingQueue = new LinkedBlockingQueue<>();
        scanner = new Scanner(System.in);

        Thread inputThread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    continue;
                }
                if (waitingForInput) {
                    System.out.print("> ");
                    String line = scanner.nextLine();
                    blockingQueue.offer(line);
                    waitingForInput = false;
                }
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    /**
     * Get the BlockingQueue in use by this View (UI).
     * This blocking queue is necessary to correctly pass user input between the View and the
     * 'controller' (MVC) class.
     * @return The BlockingQueue that will be used by the 'controller' (MVC) class to take user input.
     */
    @Override
    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
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
     * Make the view ready to receive user input.
     */
    @Override
    public void setWaitingForInput()
    {
        this.waitingForInput = true;
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
