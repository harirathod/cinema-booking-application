package com.cinema.cinema;

/**
 * This class is to be used when the customer is making a booking.
 *
 * This class implements the functionality of commands and evaluates them.
 * @author Hari Rathod
 * @version 2023.02.05
 */

public class CustomerBooking {
    private Parser parser;
    private TicketOffice office;

    /**
     * Constructor to initialise fields.
     */
    public CustomerBooking()
    {
        parser = new Parser();
        office = new TicketOffice();
    }

    /**
     * Starts the booking, and continue to process user input until the user quits the application.
     */
    public void start()
    {
        populateCinema();

        System.out.println("Welcome to Glacier Cinema!");
        boolean finished = false;
        while(!finished) {
            // While the user is not finished, get the next command and evaluate it.
            finished = evaluateCommand(parser.readInput());
        }
        System.out.println("Thanks for visiting, and have a great time!");
        System.out.println(getSeparator());
    }

    /**
     * Process and evaluate the command entered by calling appropriate methods.
     * If null is entered, it evaluates the command as main.cinema.Command.UNKNOWN.
     * @param command The command to evaluate.
     * @return True if the user wants to quit, false if not.
     */
    public boolean evaluateCommand(Command command)
    {
        // Print a line of dashes for readability.
        System.out.println(getSeparator());

        // Evaluate the command.
        if(command == null) {
            unknown();
            return false;
        }
        switch (command.getCommandWord()) {
            case HELP -> help();
            case BOOK -> book(command);
            case LIST -> list(command);
            case QUIT -> { return true; }
            default -> unknown();
        }

        // Print a line of dashes for readability.
        System.out.println(getSeparator());
        return false;
    }

    /**
     * Provide helpful information for the user.
     */
    private void help()
    {
        System.out.println("With our booking platform you can book movies, etc.");
        System.out.println(parser.getAllCommands());
    }

    /**
     * Books a random seat for given movie title. Parameter should be passed at 'BOOK movie', where 'movie' is allowed
     * to consist of spaces. If the movie does not exist, nothing will be booked, and an error will be printed.
     * Throws NullPointerException if 'null' is passed in.
     * @param command The command should be entered as 'BOOK movie'.
     */
    private void book(Command command)
    {
        for (String movieTitle : office.getAllMovieTitles()) {
            if(movieTitle.toLowerCase().equals(command.getSecondWord())) {
                Ticket ticket = office.bookRandomTicket(movieTitle);
                System.out.println(ticket.getDetails());
                return;
            }
        }
        /* if a movie title has not been matched with the movie booking requested, only then will the bookError() line be
        reached. Otherwise, the method will return once a match is found. */
        bookError();
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
    private void bookError()
    {
        System.out.println("Sorry, we couldn't find the movie you were looking for.");
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
    {
        office.addScreen(1, 12, 26);
        office.addScreen(2, 20, 20);

        office.addNewMovie(1, "Black Panther 3", 1395);
        office.addNewMovie(2, "Batman - Dark of the Moon", 1300);
    }
}
