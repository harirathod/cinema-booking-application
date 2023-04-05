package com.cinema.cinema;

import java.util.*;
import java.util.Map.Entry;

/**
 * TicketOffice is manages the other class. It is through this class
 * that screens and movies can be added, and tickets can be bought.

 * @author Hari Rathod
 * @version 2022.12.08
 */
public class TicketOffice
{
    // All screens in the Cinema.
    private Map<Integer, Screen> screens;

    /**
     * Initialise fields.
     */
    public TicketOffice()
    {
        screens = new HashMap<>();
    }

    /**
     * Add a screen to the cinema. If the number of columns or row for the
     * screen is invalid (< 0), no screen will be added. If the screen already
     * exists, another screen cannot be added.
     *
     * @param id              The id of the screen. Must be a positive number.
     * @param numberOfColumns The number of columns for the screen. Must be a positive number.
     * @param numberOfRows    The number of rows for the screen. Must be a positive number.
     * @throws InvalidScreenParameterException If any of the parameters for the construction of a Screen object are invalid.
     * @throws ScreenIdAlreadyExistsException  If a Screen object, with an id matching the id parameter provided, already exists in this TicketOffice.
     */
    public void addScreen(int id, int numberOfColumns, int numberOfRows) throws InvalidScreenParameterException, ScreenIdAlreadyExistsException {
        Screen screen;
        try {
            screen = new Screen(id, numberOfColumns, numberOfRows);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidScreenParameterException("Number of columns (" + numberOfColumns + ") and number of rows ("
                    + numberOfRows + ") invalid for Screen.");
        }

        if (screens.containsKey(id)) {
            throw new ScreenIdAlreadyExistsException("Screen with id " + id + " already exists.");
        }
        screens.put(id, screen);
    }

    /**
     * Remove screen from the cinema. If the screen is not in the cinema, nothing will happen.
     *
     * @param id The id of the screen to be removed.
     * @return True if the screen was successfully removed, false otherwise.
     */
    public boolean removeScreen(int id)
    {
        Iterator<Entry<Integer, Screen>> it = screens.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Add a screen to the cinema. If the screen id has already been added,
     * adding another screen with the same id will have no effect.
     *
     * @param screen The screen to be added.
     * @throws ScreenIdAlreadyExistsException If the id of the screen that we are trying to add is already present.
     */
    public void addScreen(Screen screen) throws ScreenIdAlreadyExistsException
    {
        if(screen == null) {
            throw new IllegalArgumentException("Screen cannot be null.");
        }

        if (screens.containsKey(screen.getId())) {
            throw new ScreenIdAlreadyExistsException("Screen with id " + screen.getId() + " already exists.");
        }
        screens.put(screen.getId(), screen);
    }

    /**
     * Get a screen using the provided id.
     *
     * @param id The id of the screen to get.
     * @return The screen that matches the id, or null if the screen is not found.
     */
    public Screen findScreen(int id)
    {
        return screens.get(id);
    }

    /**
     * Show a new movie at a specific screen. No movie is added if there
     * is no screen with the provided id.
     *
     * @param id         The id of the screen we want to add the movie to.
     * @param movieTitle The title of the movie.
     * @param ticketCost The cost of a ticket (in cents).
     * @throws ScreenIdDoesNotExistException If the screen with the id parameter provided does not exist.
     */
    public void addNewMovie(int id, String movieTitle, int ticketCost) throws ScreenIdDoesNotExistException
    {
        if (!screens.containsKey(id)) {
            throw new ScreenIdDoesNotExistException("Screen with id (" + id
                    + ") does not exist.");
        }

        screens.get(id).addNewMovie(movieTitle, ticketCost);
    }

    /**
     * Get the details of all the movies currently showing at the cinema, formatted as a String.
     * @return The details of all movies, formatted as a String.
     */
    public String getAllMoviesDetails()
    {
        StringBuilder details = new StringBuilder();
        screens.values().stream()
                .filter(Screen::hasMovieScreening)
                .map(Screen::getDetails)
                .forEach(details::append);
        return details.toString();
    }

    /**
     * Book a random ticket for a movie.
     *
     * @param movieTitle The movie to book a random ticket for.
     * @return A ticket to the movie, chosen at random.
     * @throws NoAvailableSeatException   If there are no available seats for the screening of the movie.
     * @throws MovieDoesNotExistException If the movie is not being screened.
     */
    public Ticket bookRandomTicket(String movieTitle) throws NoAvailableSeatException, MovieDoesNotExistException
    {

        // Validate that the movie is being screened.
        validateMovieTitle(movieTitle);

        // Get the screen that is screening the movie.
        Screen screen = screens.values().stream()
                .filter(x -> movieTitle.equals(x.getMovieTitle()))
                .findFirst().get();

        boolean booked = false;
        Ticket ticket = null;
        
        // Only exist the loop once a random ticket is booked, or if there are absolutely zero seats available.
        while (!booked) {
            try {
                // If there are no seats available, 'NoAvailableSeatException' will be thrown.
                ticket = screen.bookRandomTicket();
                booked = true;
            }
            catch (UnavailableSeatException e) {
                continue;
            }
        }
        return ticket;
    }

    /**
     * Get the Screen that is screening a movie.
     *
     * @param movie The movie to search for.
     * @return The Screen that is screening the movie.
     * @throws MovieDoesNotExistException If there is no screening of this movie.
     */
    public Screen validateMovieTitle(String movie) throws MovieDoesNotExistException
    {
        return screens.values().stream()
                .filter(x -> movie.equals(x.getMovieTitle()))
                .findFirst().orElseThrow(() -> new MovieDoesNotExistException("Movie " + movie + " is not being screened."));
    }

    /**
     * Book a ticket for a movie.
     * @param movieTitle The movie to book a ticket for.
     * @param seatNumber The seat number, i.e., how far across in a given row (the column number).
     * @param rowNumber The row number, i.e., how close to the screen.
     * @return A ticket to the movie.
     * @throws MovieDoesNotExistException If the movie is not being screened.
     * @throws UnavailableSeatException If the seat is already booked.
     */
    public Ticket bookTicket(String movieTitle, int seatNumber, int rowNumber) throws MovieDoesNotExistException, UnavailableSeatException
    {
        // Check that the movie is being screened.
        validateMovieTitle(movieTitle);

        return screens.values().stream()
                    .filter(x -> movieTitle.equals(x.getMovieTitle()))
                    .findFirst().get().bookTicket(seatNumber, rowNumber);
    }
}
