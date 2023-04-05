package com.cinema.cinema;

import java.io.IOException;

/**
 * This class contains the main method that sets up the database with screens.
 * @author hari_rathod
 */
public class SetUpDatabase {

    /**
     * Main method that sets up the database.
     * @param args Should be left blank.
     */
    public static void main(String[] args) {
        try {
            ScreenDataManipulator screenDataManipulator = new ScreenDataManipulator();
            Screen screen = new Screen(1, 10, 12);
            Screen screen2 = new Screen(2, 20, 32);
            screen.addNewMovie("Batman", 1300);
            screen2.addNewMovie("Shazam | Revenge of the Sock", 2100);
            screenDataManipulator.addScreen(screen);
            screenDataManipulator.addScreen(screen2);
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading database.");
        }

    }


}
