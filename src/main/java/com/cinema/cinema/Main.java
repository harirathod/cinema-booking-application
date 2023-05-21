package com.cinema.cinema;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * This is the main class with the main method that starts the application.
 * @author hari_rathod
 * @version 2023.05.21
 */
public class Main {

    /**
     * The main method that starts the application.
     * @param args Optional arguments.
     */
    public static void main(String[] args)
    {
        new CinemaBookingApplication().run(args);
    }
}
