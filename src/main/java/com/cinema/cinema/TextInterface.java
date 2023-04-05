package com.cinema.cinema;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class is to be used when the customer is making a booking.
 *
 * This class implements the functionality of commands and evaluates them.
 * @author Hari Rathod
 * @version 2023.02.05
 */

public class TextInterface {
    private Parser parser;
    private final TicketOffice office;
    private ConsoleHistoryRecorder consoleHistoryRecorder;
    private final ObjectDataRecorder<Ticket> ticketDataRecorder = new ObjectDataRecorder<>(Filename.TICKET, Ticket.class);
    private final ObjectDataRecorder<Screen> screenDataRecorder = new ObjectDataRecorder<>(Filename.SCREEN, Screen.class);

    /**
     * Constructor to initialise fields.
     */
    public TextInterface()
    {
        parser = new Parser();
        this.office = new TicketOffice();

        try {
            consoleHistoryRecorder = new ConsoleHistoryRecorder();
        } catch (IOException e) {
            System.out.println("Error writing to " + e.getMessage());
        }

        populateScreens();
    }

    /**
     * Starts the booking, and continue to process user input until the user quits the application.
     */
    public void start()
    {
        System.out.println("Welcome to Glacier Cinema!");

        // While the user is not finished, get the next command and evaluate it.
        Command command = null;
        do {
            String input = parser.readInput();
            recordInputString(input);
            command = CommandConverter.convertToCommand(input);
            System.out.println(getSeparator());
            evaluateCommand(command);
            System.out.println(getSeparator());
        }
        while (command.getCommandWord() != CommandWord.QUIT);

        System.out.println("Thanks for visiting, and have a great time!");
    }

    /**
     * Write this string to a text file.
     * @param inputString The string to be written.
     */
    private void recordInputString(String inputString)
    {
        try {
            consoleHistoryRecorder.writeStringToFile(inputString.toString());
        } catch (IOException e) {
            System.out.println("Error writing to file " + e.getMessage());
        }
    }

    /**
     * Process and evaluate the command entered by calling appropriate methods.
     * If null is entered, it evaluates the command as Command.UNKNOWN.
     *
     * @param command The command to evaluate.
     */
    public void evaluateCommand(Command command)
    {
        // Evaluate the command.
        switch (command.getCommandWord()) {
            case HELP -> help();
            case BOOK -> book();
            case LIST -> list(command);
            case BASKET -> showTickets();
            case QUIT -> {}
            default -> unknown();
        }
    }

    /**
     * Provide helpful information for the user.
     */
    private void help()
    {
        System.out.println("""
                With our booking platform you can book tickets to movies.
                These are the available commands:
                """ + parser.getAllCommands());
    }

    /**
     * Allows the user to book a seat for a movie, by prompting them to enter the name of the movie, then the
     * seat numbers.
     */
    private void book()
    {
        System.out.println("Which movie would you like to book a ticket for?");
        String movie = parser.readInput();

        Screen screen;
        try {
            // Get the screen that is screening the movie.
            screen = office.validateMovieTitle(movie);
        } catch (MovieDoesNotExistException e) {
            bookingError(e.getMessage());
            return;
        }

        System.out.println("Current screening of the movie:\n" + screen.getDetails());
        System.out.println("Which seat would you like to book?");
        String[] seatPosition;

        // Use regex to check the row and column values entered are parsable integers.
        Pattern numberPattern = Pattern.compile("\\d+");
        do {
            System.out.println("Please provide the seat as '<column>, <row>'. Example: 3, 4");
            seatPosition = parser.readInputAsArray();
        } while (seatPosition.length < 2 || (!(numberPattern.matcher(seatPosition[0]).matches() && numberPattern.matcher(seatPosition[1]).matches())));

        int columnNumber = Integer.parseInt(seatPosition[0]);
        int rowNumber = Integer.parseInt(seatPosition[1]);
        try {
            // Check that the seat numbers are a valid position for that screen.
            screen.validateSeatNumbers(columnNumber, rowNumber);
        } catch (InvalidSeatException e) {
            System.out.println(e.getMessage());
        }

        try {
            Ticket ticket = office.bookTicket(movie, columnNumber, rowNumber);
            try {
                // Store the ticket in the ticket data file.
                ticketDataRecorder.writeToFile(ticket);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("There was an error saving your ticket.");;
            }
        } catch (UnavailableSeatException | MovieDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print an error message, if there is an error with booking.
     */
    private void bookingError(String message)
    {
        System.out.println("## " + message + " ##");
    }

    /**
     * List all the movies being shown at the cinema.
     * @param command The command should have no second word. The purpose of the parameter being passed, is to
     *               ensure the user is not expecting something different to be listed than what is programmed, if they
     *                type 'list timings', for example.
     */
    private void list(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Please do not enter any arguments after 'list'.");
            return;
        }
        String detailsOfMovies = office.getAllMoviesDetails();
        if (detailsOfMovies.isEmpty()) {
            System.out.println("No movies currently showing.");
        } else {
            System.out.print(detailsOfMovies);
        }
    }

    /**
     * Print all tickets that the user has stored
     */
    private void showTickets()
    {
    }

    /**
     * Print an error message, if the command is unrecognised.
     */
    private void unknown()
    {
        System.out.println("Sorry, we didn't understand what you meant.\nPlease enter 'help' for more advice.");
    }

    /**
     * Return a line of dashes, that can be used to separate sections of text. Use to increase readability.
     * @return A line of dashes, to separate sections of text.
     */
    private String getSeparator()
    {
        return "    ---------------------------------    ";
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
                    System.out.println(e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error handling file " + screenDataRecorder.getFILENAME() + " " + e.getMessage());
        }
    }
}
