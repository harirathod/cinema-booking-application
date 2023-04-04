package com.cinema.cinema;

/**
 * This class contains the main method to run the application.
 * @author Hari Rathod
 * @version 2023.02.05
 */


public class Main {

    /**
     * main.cinema.Main method that starts the application.
     * @param args Should be left blank.
     */
    public static void main(String[] args) {
        // what should be here is a 'Controller' that starts the parser.
        TextInterface booking = new TextInterface(null);
        booking.start();
    }


}
