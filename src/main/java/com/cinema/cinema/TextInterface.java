package com.cinema.cinema;

import java.io.IOException;

/**
 * This class is to be used when the customer is making a booking.
 *
 * This class implements the functionality of commands and evaluates them.
 * @author Hari Rathod
 * @version 2023.02.05
 */

public class TextInterface {
    private Parser parser;
    private TicketOffice office;
    private ConsoleHistoryRecorder consoleHistoryRecorder;

    /**
     * Constructor to initialise fields.
     */
    public TextInterface(TicketOffice office)
    {
        parser = new Parser();
        if (office != null) {
            this.office = office;
        }
        else {
            this.office = new TicketOffice();
        }

        try {
            consoleHistoryRecorder = new ConsoleHistoryRecorder();
        }
        catch (IOException e) {
            System.out.println("Error writing to " + e.getMessage());
        }
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
            case BOOK -> book(command);
            case LIST -> list(command);
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
     * Books a random seat for given movie title. Parameter should be passed at 'BOOK movie', where 'movie' is allowed
     * to consist of spaces. If the movie does not exist, nothing will be booked, and an error will be printed.
     * Throws NullPointerException if 'null' is passed in.
     * @param command The command should be entered as 'BOOK movie'.
     */
    private void book(Command command)
    {
        System.out.println("Which movie would you like to book a ticket for?");
        String movie = parser.readInput();

        Screen screen;
        try{
            screen = office.validateMovieTitle(movie);
        }
        catch (MovieDoesNotExistException e) {
            bookingError(e.getMessage());
            return;
        }
        System.out.println("Current screening of the movie:\n" + screen.getDetails());
        System.out.println("Which seat would you like to book?");
        String[] seatPosition;
        do {
            System.out.println("Please provide the seat as '<column>, <row>'. Example: 3, 4");
            seatPosition = parser.readInputAsArray();
        } while (seatPosition.length < 2);
        /* TODO: Fix this method.
        screen.validateSeatNumbers(seatPosition[0], seatPosition[1]);
        try {
            Ticket ticket = office.bookTicket(movie, Integer.parseInt(seatPosition[0]), Integer.parseInt(seatPosition[1]));
        } catch (MovieDoesNotExistException | UnavailableSeatException e) {
            throw new RuntimeException(e);
        }

        /* TODO: Is this method needed?
        for (String movieTitle : office.getAllMovieTitles()) {
            if(movieTitle.toLowerCase().equals(command.getSecondWord())) {
                Ticket ticket = office.bookRandomTicket(movieTitle);
                System.out.println(ticket.getDetails());
                return;
            }
        }
        /* if a movie title has not been matched with the movie booking requested, only then will the bookError() line be
        reached. Otherwise, the method will return once a match is found. */
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
        office.showMovies();
    }

    /**
     * Print an error message, if the command is unrecognised.
     */
    private void unknown()
    {
        System.out.println("Sorry, we didn't understand what you meant.\nPlease enter 'help' for more advice.");
    }

    /**
     * Print an error message, if there is an error with booking a specific movie.
     */
    private void bookingError(String message)
    {
        System.out.println("## " + message + " ##");
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
     * Populate the cinema with movies and screens.
     */
    private void populateCinema()
    {/* TODO:
        office.addScreen(1, 12, 26);
        office.addScreen(2, 20, 20);

        office.addNewMovie(1, "Black Panther 3", 1395);
        office.addNewMovie(2, "Batman - Dark of the Moon", 1300);*/
    }
}
