package com.cinema.cinema;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class starts the manager booking and provides functionality that a manager would normally be presented with,
 * i.e., removing and adding screens from the cinema.
 * @author hari_rathod
 * @version 2023.04.24
 */
public class ManagerBooking extends Booking {

    private InputRecorder inputRecorder;

    /**
     * Constructor to initialise fields.
     * @param view The view that we want to run the manager booking with. Null must not be passed.
     */
    public ManagerBooking(View view) {
        super(view);

        try {
            inputRecorder = new InputRecorder("manager_input.txt");
        } catch (IOException e) {
            getView().displayError("There was an error writing to " + e.getMessage());
        }
    }

    /**
     * Start the manager booking.
     */
    public void start()
    {
        getView().displayWithFormatting("""
                Welcome to the manager booking system.
                Using this system, you can add and remove screens from the cinema.""");
        // Start the booking. Continue to process input until the user quits.
        super.start();
    }

    /**
     * Evaluate the command.
     * @param command The command to evaluate.
     */
    @Override
    protected void evaluateCommand(Command command)
    {
        switch (command.getCommandWord()) {
            case HELP -> help();
            case ADD -> add();
            case REMOVE -> remove();
            case LIST -> list(command);
            case QUIT -> getView().displayWithFormatting("Manager session finished.");
            default -> unknown();
        }
    }

    /**
     * Display helpful information for the user, such as what the application does and a list of all commands.
     */
    private void help()
    {
        getView().displayWithFormatting("""
                With our booking platform you, as the manager, can add and remove movies from the cinema.
                These are the available commands:""" + getBookingType().getAllCommandsAsString());
    }

    /**
     * Add a screen to the cinema.
     * @see Screen
     */
    private void add()
    {
        getView().displayWithFormatting("Please specify the screen id of the screen that you want to add the movie to.");
        // Get the id from the user, and check that it is a parsable integer.
        String string = null;
        do {
            getView().display("Please provide the id as a single number, e.g., 1");
            string = getView().getInput();
            if (string.equals(CommandWord.QUIT.getCommandString())) {
                getView().display("Adding screen process was cancelled.");
                return;
            }
        }
        while (!NumberMatcher.matchesSingleInteger(string));

        // Check that the movie exists in the cinema.
        Screen screen;
        try {
            screen = getOffice().findScreen(Integer.parseInt(string));
        } catch (ScreenIdDoesNotExistException e) {
            getView().display(e.getMessage());
            return;
        }

        // Prevent the user from overwriting screens that are currently screening a movie.
        // The user should call 'remove()' if they wish to overwrite an existing screening.
        if (screen.hasMovieScreening()) {
            getView().display("You cannot add a screen to this movie, as it already has a screening.");
            return;
        }

        getView().displayWithFormatting("Please specify the title of the movie.");
        String movieTitle = getView().getInput();
        getView().displayWithFormatting("Please specify the cost to see this movie, in cents.");

        // Get the cost from the user, and check that it is a parsable integer.
        do {
            getView().display("Please provide the cost as a single number, e.g., 1300");
            string = getView().getInput();
            if (string.equals(CommandWord.QUIT.getCommandString())) {
                getView().display("Adding screen process was cancelled.");
                return;
            }
        }
        while (!NumberMatcher.matchesSingleInteger(string));
        int cost = Integer.parseInt(string);

        screen.addNewMovie(movieTitle, cost);
        // Record the screen persistently.
        recordScreenInFile(screen);
        getView().display("Movie %s was added to screen %s.".formatted(movieTitle, screen.getId()));
    }

    /**
     * Starts the process of removing a movie screening from the cinema. This method does not remove a screen – it only
     * removes the movie that the screen is showing.
     */
    private void remove()
    {
        getView().displayWithFormatting("Which movie would you like to remove?");

        // Get the screen id from the user, and check that it is a parsable integer.
        String string;
        do {
            getView().display("Please provide the id of the screen as a number, e.g., 7.");
            string = getView().getInput();
            if (string.equals(CommandWord.QUIT.getCommandString())) {
                getView().display("Cancelled process of removing screen.");
                return;
            }
        }
        while (!NumberMatcher.matchesSingleInteger(string));

        // Check that the movie exists in the cinema.
        Screen screen;
        try {
            screen = getOffice().findScreen(Integer.parseInt(string));
        } catch (ScreenIdDoesNotExistException e) {
            getView().display(e.getMessage());
            return;
        }
        String movieTitle = screen.getMovieTitle();
        screen.removeMovie();
        recordScreenInFile(screen);
        getView().display("Movie %s was remove from screen %s.".formatted(movieTitle, screen.getId()));
    }

    /**
     * Record a screen in the list of screens being stored. This screen overwrites any existing screens with the same id.
     * If a screen in the list doesn't match this new screen's id, then the new screen is added without overwriting any existing screens.
     * @param newScreen The new screen to be added to the screens currently being stored.
     */
    private void recordScreenInFile(Screen newScreen)
    {
        List<Screen> screens = null;
        try {
            screens = getScreenDataRecorder().readListOfObjectsFromFile();
        } catch (IOException | ClassNotFoundException e) {
            getView().displayError("There was an error reading the current screens from file.");
            return;
        }

        screens.removeIf(screen -> screen.getId() == newScreen.getId());
        screens.add(newScreen);
        screens.sort(Comparator.comparingInt(Screen::getId));
        try {
            getScreenDataRecorder().resetFile();
            for (Screen screen : screens) {
                getScreenDataRecorder().writeToFile(screen);
            }
        } catch (IOException | ClassNotFoundException e) {
            getView().displayError("There was an error saving the updated screen to file.");
        }
    }

    /**
     * Returns the booking type. This should be 'BookingType.MANAGER'.
     * @return The booking type.
     */
    @Override
    protected BookingType getBookingType()
    {
        return BookingType.MANAGER;
    }

    /**
     * Get the InputRecorder that this class wants to record input in.
     * @return The InputRecorder to record input in.
     */
    @Override
    protected InputRecorder getInputRecorder()
    {
        return inputRecorder;
    }


}
