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
            DatabaseManipulator databaseManipulator = new DatabaseManipulator();
            Screen screen = new Screen(1, 10, 12);
            screen.addNewMovie("Batman", 1300);
            databaseManipulator.addScreen(screen);
            databaseManipulator.addScreen(new Screen(2, 20, 32));
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading database.");
        }

    }


}
