package com.cinema.cinema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Class Screen represents a screen (i.e., a movie room) in a multiplex cinema.
 *
 * @author Hari Rathod
 * @version 2023.03.28
 */
public class Screen
{
    private String movieTitle;

    // Whether the screen is currently showing a movie.
    private boolean hasMovieScreening;
    private int ticketCost;

    // The seats in the screen.
    private Seat[][] seats;

    // The id of the screen.
    private int id;

    /**
     * Initialise fields.
     * @param id The id of the screen.
     * @param numberOfColumns The number of columns the screen should have. Must be >= 1.
     * @param numberOfRows The number of rows the screen should have. Must be >= 1.
     * @throws IllegalArgumentException If the number of columns or rows is less than 1.
     */
    public Screen(int id, int numberOfColumns, int numberOfRows)
    {
        if (numberOfColumns < 1 || numberOfRows < 1) {
            throw new IllegalArgumentException("Number of columns and rows must be greater than 0.");
        }

        // No movie is being shown by default.
        hasMovieScreening = false;

        this.id = id;

        // Create a grid of seats.
        seats = new Seat[numberOfColumns][numberOfRows];
        for(int i = 0; i < seats.length; i++) {
            for(int  j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat();
            }
        }
    }

    /**
     * Empty the screen, i.e., make every seat available.
     */
    public void emptyScreen()
    {
        for (Seat[] rowOfSeats : seats) {
            for (Seat seat : rowOfSeats) {
                seat.setAvailable();
            }
        }
    }

    /**
     * Get the number of available seats for this Screen.
     * @return The number of available seats.
     */
    public int getNumberOfAvailableSeats()
    {
        int availableSeatsCount = 0;
        for (Seat[] seatColumn : seats) {
            for (Seat seat : seatColumn) {
                if (seat.isAvailable()) {
                    availableSeatsCount++;
                }
            }
        }
        return availableSeatsCount;
    }

    /**
     * Get the total number of seats.
     * @return The total number of seats.
     */
    public int getNumberOfSeats()
    {
        return seats.length * seats[0].length;
    }

    /**
     * Take the seat and row number, and mark that seat as booked.
     * @param columnNumber The column number of the seat to book. 1 is the number of the first column.
     * @param rowNumber The row number of the seat to book. 1 is the number of the first row.
     * @throws UnavailableSeatException If the seat is unavailable (booked).
     */
    protected void book(int columnNumber, int rowNumber) throws UnavailableSeatException {


        // Entering a value of 1 should access element 0 of the grid of seats.
        columnNumber = columnNumber - 1;
        rowNumber = rowNumber - 1;

        if (!seats[columnNumber][rowNumber].isAvailable()) {
            throw new UnavailableSeatException("Seat is unavailable.");
        }
        seats[columnNumber][rowNumber].setUnavailable();
    }

    /**
     * Change the screen to show a new movie, with a new ticket cost.
     * @param newMovieTitle The new title of the movie.
     * @param ticketCost The cost of a ticket (in cents), to see this movie.
     */
    public void addNewMovie(String newMovieTitle, int ticketCost)
    {
        movieTitle = newMovieTitle;
        hasMovieScreening = true;
        this.ticketCost = ticketCost;
        emptyScreen();
    }

    /**
     * Remove the movie from the Screen.
     */
    public void removeMovie()
    {
        movieTitle = null;
        hasMovieScreening = false;
        ticketCost = 0;
    }

    /**
     * Get the details of the Screen, including id, current movie, and ticket
     * cost.
     * @return The details of the screen.
     */
    public String getDetails()
    {
        String details = "";
        details += "ID: " + id + "\n";
        details += "Current Movie: '" + movieTitle + "'\n";
        details += "Seat capacity: " + seats.length + " columns, by " + seats[0].length + " rows.\n";
        details += "Ticket Cost: £" + (ticketCost / 100 );

        return details;
    }

    /**
     * Book a random ticket.
     * @return A ticket object if there is at least one available seat.
     * @throws NoAvailableSeatException If there are no seats available.
     * @throws UnavailableSeatException If the seat randomly chosen to book is unavailable.
     */
    public Ticket bookRandomTicket() throws NoAvailableSeatException, UnavailableSeatException {

        // Check that at least 1 seat is available.
        checkSeatAvailability();

        Random random = new Random();
        int randomColumnNumber = random.nextInt(seats.length) + 1;
        int randomRowNumber = random.nextInt(seats[0].length) + 1;

        book(randomColumnNumber, randomRowNumber);

        return new Ticket(id, movieTitle, randomColumnNumber, randomRowNumber,
                ticketCost, LocalDateTime.now());
    }

    /**
     * Check if there are any available seats.
     * @throws NoAvailableSeatException If there is no available seat.
     */
    private void checkSeatAvailability() throws NoAvailableSeatException
    {
        if (getNumberOfAvailableSeats() == 0) {
            throw new NoAvailableSeatException("No available seats. All seats are booked for this screen.");
        }
    }

    /**
     * Get a specified seat.
     * @return A ticket object if the seat is available, else return null.
     * @throws UnavailableSeatException If the seat chosen to book is unavailable.
     */
    public Ticket bookTicket(int columnNumber, int rowNumber) throws UnavailableSeatException
    {
        book(columnNumber, rowNumber);
        return new Ticket(id, movieTitle, columnNumber, rowNumber, ticketCost,
                LocalDateTime.now());
    }

    /**
     * Validate the seat numbers.
     * @param columnNumber The column number of the seat.
     * @param rowNumber The row number of the seat.
     * @throws InvalidSeatException If the seat is an invalid position in this Screen.
     */
    public void validateSeatNumbers(int columnNumber, int rowNumber) throws InvalidSeatException
    {
        if (columnNumber < 1 || columnNumber > seats.length || rowNumber < 1 || rowNumber > seats[0].length) {
            String error = "Seat position (" + columnNumber + ", " + rowNumber + ") out of range for "
                    + "available seats " + seats.length + seats[0].length;
            throw new InvalidSeatException(error);
        }
    }

    /**

    /**
     * Get the title of the movie being shown.
     * @return The title of the movie.
     */
    public String getMovieTitle()
    {
        return movieTitle;
    }

    /**
     * Get the id of the screen.
     * @return The id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Check whether the screen is currently showing a movie.
     * @return True if the screen is showing a movie, false if it isn't showing a movie.
     */
    public boolean hasMovieScreening()
    {
        return hasMovieScreening;
    }

    /**
     * Get the cost (in cents) of a ticket for the movie.
     * @return The cost of a ticket to see the movie.
     */
    public int getTicketCost()
    {
        return ticketCost;
    }
}
