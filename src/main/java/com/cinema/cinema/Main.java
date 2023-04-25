package com.cinema.cinema;

import javafx.application.Application;

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

        // TODO: Apply Singleton pattern to BookingChooser so we can do getInput(). Add BlockingQueue to BookingChooser
        //  to get the user input. If input = 'manager' start new ManagerBooking(new GuiViewController).start(), otherwise
        //  if 'customer', start new CustomerBooking.

        /*
        TODO: if args.length > 1 && args[1] == m then start manager booking with TextView, otherwise start customer booking,
         also with text view.
         */
        //new ManagerBooking(new TextView()).start();
        new Thread(() -> {
            Application.launch(BookingChooser.class);
        }).start();

    }
}
