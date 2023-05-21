package com.cinema.cinema;

import javafx.application.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This class decides which Booking and View subclass to instantiate.
 * If the user is running the application from the command line:
 *          If they provide any arguments after the 'java -jar cinema.jar' then this class assumes they want to
 *          run the application in the terminal. Then, if the command line argument is "m", this class starts a
 *          ManagerBooking. If the argument is "c", this class starts a CustomerBooking.
 *
 *          Else, if they do not provide any arguments after 'java -jar cinema.jar', the GUI view is launched, where
 *          the user is prompted to start a ManagerBooking or CustomerBooking.
 * Else, if the user is not running the application from the command line (i.e., they double click the .jar), then
 * the GUI view is launched.
 * @author hari_rathod
 * @version 2023.05.02
 */
public class CinemaBookingApplication {

    /**
     * This method decides whether the run the application as a GUI or in the terminal.
     * If no arguments are passed into this method, the GUI is launched. Otherwise, the booking starts in the terminal.
     * If "m" is passed, the ManagerBooking starts in the terminal. If "c" is passed, a CustomerBooking starts.
     * @param args The arguments for this application. If "m" is provided as the argument, a ManagerBooking starts in the
     *             terminal. If "c" is provided, then a CustomerBooking starts.
     */
    public void run(String[] args)
    {
        // If the user uses the command line or enters arguments, then open the booking application in the terminal.
        if (args != null && args.length > 0) {
            switch (args[0]) {
                case "m" -> new ManagerBooking(new TextView()).start();
                case "c" -> new CustomerBooking(new TextView()).start();
                default -> System.out.println("Failed to recognise provided arguments.");
            }
        }
        else {
            // If the user doesn't use arguments to start the application, then open the Booking as a GUI.

            // Using an Executor over new Thread(...).start() increases performance (by up to 2000 milliseconds).
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(() -> {
                Application.launch(BookingChooser.class);
            });
            BookingChooser bookingChooser = BookingChooser.getBookingChooser();

            // Waits (blocks) until the user has submitted their selected booking type.
            BookingType bookingType = bookingChooser.getSelectedBookingType();
            switch (bookingType) {
                case MANAGER -> new ManagerBooking(new GuiViewController()).start();
                case CUSTOMER -> new CustomerBooking(new GuiViewController()).start();
            }
        }
    }
}
