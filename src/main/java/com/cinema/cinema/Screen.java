package com.cinema.cinema;

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
    private int availableSeats;

    // The seats in the screen.
    private Seat[][] seats;

    // The id of the screen.
    private int id;

    /**
     * Initialise fields.
     * @param id The id of the screen.
     * @param numberOfColumns The number of columns the screen should have.
     * @param numberOfRows The number of rows the screen should have.
     * @throws IllegalArgumentException If the number of columns or rows is less than 1.
     */
    public Screen(int id, int numberOfColumns, int numberOfRows)
    {
        if(numberOfColumns < 1 || numberOfRows < 1) {
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
        availableSeats = getNumberOfSeats();
    }

    /**
     * Get number of available seats.
     */
    public int getNumberOfAvailableSeats()
    {
        return availableSeats;
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
     * Take the seat's seat and row number, and mark that seat as booked.
     * Return false if the seat was already booked. Return true if the seat
     * was not already booked, to show the booking has been confirmed.
     * @return True if the booking was successful, false if it was not (if
     * the seat was already booked).
     */
    protected boolean book(int seatNumber, int rowNumber)
    {
        // entering a value of 1 should access element 0 of the array
        seatNumber = seatNumber - 1;
        rowNumber = rowNumber - 1;

        if(seatNumber < 0 || seatNumber > seats.length - 1
                || rowNumber < 0 || rowNumber > seats[0].length - 1) {
            return false;
        }
        if(!seats[seatNumber][rowNumber].isAvailable()) {
            return false;
        }
        seats[seatNumber][rowNumber].setUnavailable();
        availableSeats--;
        return true;
    }

    /**
     * Change the screen to show a new movie, with a new ticket cost.
     * @param newMovieTitle The new title of the movie.
     * @param ticketCost The cost of a ticket (in cents) to see this movie.
     */
    public void addNewMovie(String newMovieTitle, int ticketCost)
    {
        movieTitle = newMovieTitle;
        hasMovieScreening = true;
        this.ticketCost = ticketCost;
        emptyScreen();
    }

    /**
     * Stop screening a movie.
     */
    public void removeMovie()
    {
        movieTitle = null;
        hasMovieScreening = false;
        ticketCost = 0;
    }

    /**
     * Get the details of the screen, including id, current movie, and ticket
     * cost.
     * @return The details of the screen.
     */
    public String getDetails()
    {
        String details = "";
        details += "ID: " + id + "\n";
        details += "Current Movie: " + movieTitle + "\n";
        details += "main.cinema.Ticket Cost: " + ticketCost;
        return details;
    }

    /**
     * Get a random ticket.
     * @return A ticket object if there is at least one available seat,
     * otherwise return null.
     */
    public Ticket getRandomTicket()
    {
        if(seats == null) {
            return null;
        }
        int randomRowNumber = 0;
        int randomSeatNumber = 0;

        do {
            randomRowNumber = new Random().nextInt(seats[0].length);
            randomSeatNumber = new Random().nextInt(seats.length);
        } while(!seats[randomSeatNumber][randomRowNumber].isAvailable());

        book(randomSeatNumber + 1, randomRowNumber + 1);

        return new Ticket(id, movieTitle, randomSeatNumber + 1, randomRowNumber + 1,
                ticketCost, LocalDateTime.now());
    }

    /**
     * Get a specified seat.
     * @return A ticket object if the seat is available, else return null.
     */
    public Ticket getTicket(int seatNumber, int rowNumber)
    {
        book(seatNumber, rowNumber);
        return new Ticket(id, movieTitle, seatNumber, rowNumber, ticketCost,
                LocalDateTime.now());
    }

    /**
     * Get the title of the movie showing in a screen.
     * @return The title of the movie being shown.
     */
    public String getMovieTitle()
    {
        return movieTitle;
    }

    /**
     * Get the id of the screen.
     * @return The id of the screen.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Check whether the screen is currently showing any movies.
     * @return True if the screen is showing any movies, false if it isn't showing movies.
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

