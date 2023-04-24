package com.cinema.cinema;

import java.io.IOException;

/**
 * The abstract class that CustomerBooking and ManagerBooking inherit from.
 */
public abstract class Booking {
    private final ObjectDataRecorder<Screen> screenDataRecorder = new ObjectDataRecorder<>(Filename.SCREEN, Screen.class);

    private View view;

    /**
     * Initialise fields.
     */
    public Booking(View view)
    {
        this.view = view;
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
     * Start the booking, and continue to take input from the user until the user quits.
     * Each time the user enters a command, the command is recorded. The way that the command is recorded is
     * defined by the subclass.
     * The evaluation of the command is carried out by the subclass.
     */
    public <E extends CommandWord> void start()
    {
        // While the user is not finished, get the next command and evaluate it.
        Command<?> command;
        do {
            // Get the user input from the 'View' (i.e., the user interface), and record it in a file.
            String input = getView().getInput();
            recordInputString(input);
            command = CommandConverter.<E>convertToCommand(input, getCommandWordType());
            evaluateCommand(command);
        }
        while (command.getCommandWord() !=  getCommandWordType().getQuitCommand());
    }

    /**
     * Get type of command word.
     * @return The type of command word.
     */
    protected abstract <E extends CommandWord> E getCommandWordType();

    /**
     * Evaluate the command.
     * @param command The command to evaluate.
     */
    protected abstract <E extends CommandWord> void evaluateCommand(Command<E> command);

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
     * Get the InputRecorder used to record input. This is defined by the subclass.
     * @return The InputRecorder used to record input.
     */
    protected abstract InputRecorder getInputRecorder();

    /**
     * Display an error message, if the command is unrecognised.
     */
    protected void unknown()
    {
        getView().displayWithFormatting("Sorry, we didn't understand what you meant.\nPlease enter 'help' for more advice.");
    }

}
