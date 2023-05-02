package com.cinema.cinema;

import javafx.application.Application;

import java.util.Arrays;

/**
 * This class contains the main method that starts the application.
 * @author hari_rathod
 */
public class CinemaBookingApplication {

    /**
     * The main method that starts the application.
     */
    public void run(String[] args) {

        // TODO: Apply Singleton pattern to BookingChooser so we can do getInput(). Add BlockingQueue to BookingChooser
        //  to get the user input. If input = 'manager' start new ManagerBooking(new GuiViewController).start(), otherwise
        //  if 'customer', start new CustomerBooking.

        /*
        TODO: if args.length > 1 && args[1] == m then start manager booking with TextView, otherwise start customer booking,
         also with text view.
         */

        System.out.println(args.length);
        // If the user uses the command line or enters arguments, then open the booking application in the terminal.
        if (args != null && args.length > 0) {
            if (args[0].equals("m")) {
                new ManagerBooking(new TextView()).start();
            } else if (args[0].equals("c")) {
                new CustomerBooking(new TextView()).start();
            }
        } else {
            // If the user doesn't use the command line to start the application, then open the Booking as a GUI.
            new Thread(() -> {
                Application.launch(BookingChooser.class);
            }).start();

            BookingChooser bookingChooser = BookingChooser.getBookingChooser();
            BookingType bookingType = bookingChooser.getSelectedBookingType();
            if (bookingType == BookingType.MANAGER) {
                new ManagerBooking(new GuiViewController()).start();
            } else if (bookingType == BookingType.CUSTOMER) {
                new CustomerBooking(new GuiViewController()).start();
            }
        }

    }
}
