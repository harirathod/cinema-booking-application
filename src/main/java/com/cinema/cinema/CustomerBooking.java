package com.cinema.cinema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This class is to be used when the customer is making a booking.
 * This class implements the functionality of commands and evaluates them.
 * @author Hari Rathod
 * @version 2023.02.05
 */

public class CustomerBooking extends Booking {


    private InputRecorder inputRecorder;

    private final ObjectDataRecorder<Ticket> ticketDataRecorder = new ObjectDataRecorder<>(Filename.TICKET, Ticket.class);

    /**
     * Constructor to initialise fields.
     * @param view The view that we want to run the customer booking with. Null must not be passed.
     */
    public CustomerBooking(View view) {
        super(view);

        try {
            ticketDataRecorder.resetFile();
        } catch (IOException e) {
            getView().displayError("There was an error resetting ticket history." + e.getMessage());
        }

        try {
            inputRecorder = new InputRecorder("user_input_history.txt");
        } catch (IOException e) {
            getView().displayError("There was an error handling " + e.getMessage());
        }
    }

    /**
     * Take input from the View (UI) and continues to process input until the user enters the String
     * equivalent of CommandWord.QUIT.
     */
    public void start()
    {
        getView().displayWithFormatting("""
                Welcome to Glacier Cinema!
                We're the brand new cinema on the scene,
                and we've got all the popular movies in screen!
                You won't go wrong with us.""");

        // Start the booking. Continue to process input until the user quits.
        super.start();

        getView().displayWithFormatting("Thanks for visiting, and have a great time!");
    }

    /**
     * Process and evaluate the command entered by calling appropriate methods.
     * If null is entered, it evaluates the command as CommandWord.UNKNOWN.
     * @param command The command to evaluate.
     */
    protected void evaluateCommand(Command command) {
        // Evaluate the command.
        switch (command.getCommandWord()) {
            case HELP -> help();
            case BOOK -> {
                try {
                    book();
                } catch (InterruptedException e) {
                    getView().displayError("There was an error with your booking.", "Apologies, this is on our side not yours..");
                }
            }
            case LIST -> list(command);
            case BASKET -> showTickets();
            case SAVE -> saveTickets();
            case QUIT -> {}
            default -> unknown();
        }
    }

    /**
     * Display helpful information for the user, such as what the application does and a list of all commands.
     */
    private void help()
    {
        getView().displayWithFormatting("""
                With our booking platform you can book tickets to movies.
                These are the available commands:""" + getBookingType().getAllCommandsAsString());
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
        getView().displayWithFormatting("Which movie would you like to book a ticket for?");
        String movie = getView().getInput();

        Screen screen;
        try {
            // Get the screen that is screening the movie.
            screen = getOffice().validateMovieTitle(movie);
        } catch (MovieDoesNotExistException e) {
            getView().displayError(e.getMessage());
            return;
        }

        getView().display("Current screening of the movie:\n" + screen.getDetails());
        getView().displayWithFormatting(String.format("Which seat would you like to book to '%s'?", screen.getMovieTitle()));
        String[] seatPosition;

        // Use regex to check the row and column values entered are parsable integers.
        do {
            getView().display("Please provide the seat as '<column>, <row>'. Example: 3, 4");
            String input = getView().getInput();
            seatPosition = StringSplitter.splitByPunctuation(input);
            if (input.equals(CommandWord.QUIT.getCommandString())) {
                getView().display("Booking cancelled.");
                return;
            }
        } while (seatPosition.length < 2 || (!(NumberMatcher.matchesSingleInteger(seatPosition[0]) && NumberMatcher.matchesSingleInteger(seatPosition[1]))));

        int columnNumber = Integer.parseInt(seatPosition[0]);
        int rowNumber = Integer.parseInt(seatPosition[1]);
        try {
            // Check that the seat numbers are a valid position for that screen.
            screen.validateSeatNumbers(columnNumber, rowNumber);
        } catch (InvalidSeatException e) {
            getView().displayError(e.getMessage());
            return;
        }

        try {
            Ticket ticket = getOffice().bookTicket(movie, columnNumber, rowNumber);
            try {
                // Store the ticket in the tickets data file.
                ticketDataRecorder.writeToFile(ticket);
                getView().displayWithFormatting("Ticket successfully added to basket."
                        + "\nYou have %d tickets in your basket.".formatted(ticketDataRecorder.getNumberOfObjects()));
            } catch (IOException | ClassNotFoundException e) {
                getView().displayError("There was an error saving your ticket.");
            }
        } catch (UnavailableSeatException | MovieDoesNotExistException e) {
            getView().displayError(e.getMessage());
        }
    }

    /**
     * Print details of all tickets that the user has booked.
     */
    private void showTickets() {
        if (getDetailsOfTicketsInBasket() == null) {
            getView().displayWithFormatting("No tickets in your basket.");
        } else {
            getView().displayWithFormatting(getDetailsOfTicketsInBasket());
        }
    }

    /**
     * Save the tickets to a text file.
     */
    private void saveTickets()
    {
        String details = getDetailsOfTicketsInBasket();
        if (details == null) {
            getView().display("No tickets to save.");
            return;
        }

        File file = getView().getSelectedSaveFile();
        if (file != null) {
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(file.getPath()))) {
                writer.write(details);
                getView().display(String.format("Saved tickets to '%s'", file.getPath()));
            } catch (IOException e) {
                getView().displayError("Saving Tickets Error", "Could not save tickets to the specified location.");
            }
        }
    }

    /**
     * Get the details of all tickets in the basket. If there was an error reading the tickets from the file, an error
     * message is displayed to the user.
     * @return The details of all tickets in the basket.
     */
    private String getDetailsOfTicketsInBasket()
    {
        String details = null;
        try {
            List<Ticket> list = ticketDataRecorder.readListOfObjectsFromFile();
            if (!list.isEmpty()) {
                details = Ticket.getAllTicketsDetails(list);
            }
        } catch (IOException | ClassNotFoundException e) {
            getView().displayError("Error getting tickets from basket.");
        }
        return details;
    }

    /**
     * Returns the booking type. This should be 'BookingType.CUSTOMER'.
     * @return The booking type.
     */
    @Override
    protected BookingType getBookingType()
    {
        return BookingType.CUSTOMER;
    }

    /**
     * Get the InputRecorder that this class wants to record input in.
     * @return The InputRecorder to record input in.
     */
    protected InputRecorder getInputRecorder()
    {
        return inputRecorder;
    }

}
