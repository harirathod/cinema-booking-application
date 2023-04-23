package com.cinema.cinema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class is to be used when the customer is making a booking.
 * This class implements the functionality of commands and evaluates them.
 * @author Hari Rathod
 * @version 2023.02.05
 */

public class CustomerBooking {

    private final TicketOffice office;
    private UserInputRecorder userInputRecorder;
    private View view;
    private final ObjectDataRecorder<Ticket> ticketDataRecorder = new ObjectDataRecorder<>(Filename.TICKET, Ticket.class);
    private final ObjectDataRecorder<Screen> screenDataRecorder = new ObjectDataRecorder<>(Filename.SCREEN, Screen.class);

    /**
     * Constructor to initialise fields.
     * @param v The view that we want to run the customer booking with. Null must not be passed.
     */
    public CustomerBooking(View v) {

        office = new TicketOffice();

        view = v;

        view.start();


        try {
            ticketDataRecorder.resetFile();
        } catch (IOException e) {
            view.displayError("There was an error resetting ticket history." + e.getMessage());
        }

        try {
            userInputRecorder = new UserInputRecorder();
        } catch (IOException e) {
            view.displayError("There was an error writing to " + e.getMessage());
        }
        // Populate the ticket office with screens from "screens.ser".
        populateScreens();

    }



    /**
     * Take input from the View (UI) and continues to process input until the user enters the String
     * equivalent of CommandWord.QUIT.
     */
    public void start()
    {
        view.displayWithFormatting("""
                Welcome to Glacier Cinema!
                We're the brand new cinema on the scene,
                and we've got all the popular movies in screen!
                You won't go wrong with us.""");

        // While the user is not finished, get the next command and evaluate it.
        Command command;
        do {
            // Get the user input from the 'View' (i.e., the user interface), and record it in a file.
            String input = view.getInput();
            recordInputString(input);
            command = CommandConverter.convertToCommand(input);
            evaluateCommand(command);
        }
        while (command.getCommandWord() != CommandWord.QUIT);

        view.displayWithFormatting("Thanks for visiting, and have a great time!");
    }

    /**
     * Write this string to a text file.
     * @param inputString The string to be written.
     */
    private void recordInputString(String inputString)
    {
        try {
            userInputRecorder.writeStringToFile(inputString.toString());
        } catch (IOException e) {
            view.displayError("Error writing user history to file " + e.getMessage());
        }
    }

    /**
     * Process and evaluate the command entered by calling appropriate methods.
     * If null is entered, it evaluates the command as Command.UNKNOWN.
     *
     * @param command The command to evaluate.
     */
    public void evaluateCommand(Command command) {
        // Evaluate the command.
        switch (command.getCommandWord()) {
            case HELP -> help();
            case BOOK -> {
                try {
                    book();
                } catch (InterruptedException e) {
                    view.displayError("There was an error with your booking.", "Apologies, this is on our side not yours..");
                }
            }
            case LIST -> list(command);
            case BASKET -> showTickets();
            case SAVE -> saveTickets();
            case QUIT -> {
            }
            default -> unknown();
        }
    }

    /**
     * Display helpful information for the user, such as what the application does and a list of all commands.
     */
    private void help()
    {
        view.displayWithFormatting("""
                With our booking platform you can book tickets to movies.
                These are the available commands:""" + CommandWord.getAllCommands());
    }

    /**
     * Starts asking the user a series of questions for booking a seat to a movie. First, this method asks
     * the user for the name of the movie, and waits for the user response. Second, the user asks for the seat
     * position.
     * If there is an error with any of the stages, view.displayError(...) is called, to display the message in
     * a user-friendly way.
     * @throws InterruptedException If this thread was interrupted whilst waiting for the user input to be provided
     * by the View (UI).
     */
    private void book() throws InterruptedException {
        view.displayWithFormatting("Which movie would you like to book a ticket for?");
        String movie = view.getInput();

        Screen screen;
        try {
            // Get the screen that is screening the movie.
            screen = office.validateMovieTitle(movie);
        } catch (MovieDoesNotExistException e) {
            view.displayError(e.getMessage());
            return;
        }

        view.display("Current screening of the movie:\n" + screen.getDetails());
        view.displayWithFormatting(String.format("Which seat would you like to book to '%s'?", screen.getMovieTitle()));
        String[] seatPosition;

        // Use regex to check the row and column values entered are parsable integers.
        Pattern numberPattern = Pattern.compile("\\d+");
        do {
            view.display("Please provide the seat as '<column>, <row>'. Example: 3, 4");
            String input = view.getInput();
            seatPosition = StringSplitter.splitByPunctuation(input);
            if (input.equals(CommandWord.QUIT.getCommandString())) {
                view.display("Booking cancelled.");
                return;
            }
        } while (seatPosition.length < 2 || (!(numberPattern.matcher(seatPosition[0]).matches() && numberPattern.matcher(seatPosition[1]).matches())));

        int columnNumber = Integer.parseInt(seatPosition[0]);
        int rowNumber = Integer.parseInt(seatPosition[1]);
        try {
            // Check that the seat numbers are a valid position for that screen.
            screen.validateSeatNumbers(columnNumber, rowNumber);
        } catch (InvalidSeatException e) {
            view.displayError(e.getMessage());
            return;
        }

        try {
            Ticket ticket = office.bookTicket(movie, columnNumber, rowNumber);
            try {
                // Store the ticket in the tickets data file.
                ticketDataRecorder.writeToFile(ticket);
                view.displayWithFormatting("Ticket successfully added to basket."
                        + "\nYou have %d tickets in your basket.".formatted(ticketDataRecorder.getNumberOfObjects()));
            } catch (IOException | ClassNotFoundException e) {
                view.displayError("There was an error saving your ticket.");
            }
        } catch (UnavailableSeatException | MovieDoesNotExistException e) {
            view.displayError(e.getMessage());
        }
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
            view.displayWithFormatting("Please do not enter any arguments after 'list'.");
            return;
        }
        String detailsOfMovies = office.getAllMoviesDetails();
        if (detailsOfMovies.isEmpty()) {
            view.displayWithFormatting("No movies currently showing.");
        } else {
            view.displayWithFormatting(detailsOfMovies);
        }
    }

    /**
     * Print details of all tickets that the user has booked.
     */
    private void showTickets() {
        if (getDetailsOfTicketsInBasket() == null) {
            view.displayWithFormatting("No tickets in your basket.");
        } else {
            view.displayWithFormatting(getDetailsOfTicketsInBasket());
        }
    }

    /**
     * Save the tickets to a text file.
     */
    private void saveTickets()
    {
        String details = getDetailsOfTicketsInBasket();
        if (details == null) {
            view.display("No tickets to save.");
            return;
        }

        File file = view.getSelectedSaveFile();
        if (file != null) {
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(file.getPath()))) {
                writer.write(details);
                view.display(String.format("Saved tickets to '%s'", file.getPath()));
            } catch (IOException e) {
                view.displayError("Saving Tickets Error", "Could not save tickets to the specified location.");
            }
        }
    }

    private String getDetailsOfTicketsInBasket()
    {
        String details = null;
        try {
            List<Ticket> list = ticketDataRecorder.readListOfObjectsFromFile();
            if (!list.isEmpty()) {
                details = Ticket.getAllTicketsDetails(list);
            }
        } catch (IOException | ClassNotFoundException e) {
            view.displayError("Error getting tickets from basket.");
        }
        return details;
    }

    /**
     * Print an error message, if the command is unrecognised.
     */
    private void unknown()
    {
        view.displayWithFormatting("Sorry, we didn't understand what you meant.\nPlease enter 'help' for more advice.");
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
     *
     */
}
