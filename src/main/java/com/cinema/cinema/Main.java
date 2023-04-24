package com.cinema.cinema;

/**
 * This class contains the main method that starts the application.
 * @author hari_rathod
 */
public class Main {

    /**
     * The main method that starts the application.
     * @param args Ignore.
     */
    public static void main(String[] args) throws InterruptedException {
        new CustomerBooking(new GuiViewController()).start();

    }
}
