package com.cinema.cinema;

import java.io.IOException;
import java.util.List;

/**
 * The abstract class that CustomerBooking and ManagerBooking inherit from.
 */
public abstract class Booking {
    private final ObjectDataRecorder<Screen> screenDataRecorder = new ObjectDataRecorder<>(Filename.SCREEN, Screen.class);
    private final TicketOffice office;
    private View view;

    /**
     * Initialise fields.
     */
    public Booking(View view)
    {
        this.view = view;
        office = new TicketOffice();
        view.start();
    }

    /**
     * Start the booking, and continue to take input from the user until the user quits.
     * Each time the user enters a command, the command is recorded. The way that the command is recorded is
     * defined by the subclass.
     * The evaluation of the command is carried out by the subclass.
     */
    public void start()
    {
        // Populate the ticket office with screens from "screens.ser".
        populateScreens();

        // While the user is not finished, get the next command and evaluate it.
        Command command;
        do {
            // Get the user input from the 'View' (i.e., the user interface), and record it in a file.
            String input = getView().getInput();
            recordInputString(input);
            command = CommandConverter.convertToCommand(input, getBookingType());
            evaluateCommand(command);
        }
        while (command.getCommandWord() !=  CommandWord.QUIT);
        System.exit(0);
    }

    /**
     * Evaluate the command.
     * @param command The command to evaluate.
     */
    protected abstract void evaluateCommand(Command command);


    /**
     * List all the movies being shown at the cinema.
     * @param command The command should have no second word. The purpose of the parameter being passed, is to
     *               ensure the user is not expecting something different to be listed than what is programmed, if they
     *                type 'list timings', for example.
     */
    protected void list(Command command)
    {
        if(command.hasSecondWord()) {
            getView().displayWithFormatting("Please do not enter any arguments after 'list'.");
            return;
        }
        String detailsOfMovies = office.getAllMoviesDetails();
        if (detailsOfMovies.isEmpty()) {
            getView().displayWithFormatting("No movies currently showing.");
        } else {
            getView().displayWithFormatting(detailsOfMovies);
        }
    }

    /**
     * Display an error message, if the command is unrecognised.
     */
    protected void unknown()
    {
        getView().displayWithFormatting("Sorry, we didn't understand what you meant.\nPlease enter 'help' for more advice.");
    }

    /**
     * Populate the cinema with screens.
     */
    private void populateScreens() {
        try {
            List<Screen> screens = screenDataRecorder.readListOfObjectsFromFile();
            for (Screen screen : screens) {
                try {
                    office.addScreen(screen);
                } catch (ScreenIdAlreadyExistsException e) {
                    view.displayError(e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            view.displayError(e.getMessage());
        } catch (IOException e) {
            view.displayError("Error handling file " + screenDataRecorder.getFILENAME() + " " + e.getMessage());
        }
    }

    /**
     * Get the view.
     * @return The view.
     */
    protected View getView()
    {
        return view;
    }


    /**
     * Get the screen data recorder, used to read and write screens to a file for persistent storage.
     * @return The screen data recorder.
     */
    protected ObjectDataRecorder<Screen> getScreenDataRecorder()
    {
        return screenDataRecorder;
    }

    /**
     * Record a string in a text file.
     * @param inputString The string to be recorded.
     */
    protected void recordInputString(String inputString)
    {
        try {
            getInputRecorder().writeStringToFile(inputString);
        } catch (IOException e) {
            getView().displayError("Error writing to file " + e.getMessage());
        }
    }

    /**
     * Get the ticket office.
     * @return The ticket office.
     */
    protected TicketOffice getOffice()
    {
        return office;
    }

    /**
     * Get the booking type. This is either 'BookingType.CUSTOMER' or 'BookingType.MANAGER'.
     * @return The booking type.
     */
    protected abstract BookingType getBookingType();


    /**
     * Get the InputRecorder used to record input.
     * @return The InputRecorder used to record input.
     */
    protected abstract InputRecorder getInputRecorder();

}
