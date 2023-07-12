package com.cinema.cinema;

import java.util.*;

/**
 * TicketOffice is manages the other class. It is through this class
 * that screens and movies can be added, and tickets can be bought.

 * @author Hari Rathod
 * @version 2022.12.08
 */
public class TicketOffice
{
    // Used to interface with the screens' database.
    private ScreenDataManipulator screenDataManipulator;
    // We cannot store 'screenDataManipulator.getAllScreens()' in a field, as the storage (database) would not be
    // synced with the object references of a 'List<Screen>'.

    /**
     * Initialise fields.
     */
    public TicketOffice()
    {
        screenDataManipulator = new ScreenDataManipulator();
    }

    /**
     * Add a screen to the cinema. If the number of columns or row for the
     * screen is invalid (< 0), no screen will be added. If the screen already
     * exists, another screen cannot be added. To add a screen with the same id, 'removeScreen(id)' must be called first.
     * Otherwise, a ScreenIdAlreadyExistsException will be thrown.
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
        addScreenToStorage(screen);
    }

    /**
     * Remove (delete) a screen from the cinema. If the screen is not in the cinema, nothing will happen.
     *
     * @param id The id of the screen to be removed.
     * @throws ScreenIdDoesNotExistException If the screen id does not exist (i.e., the id is not matched by any screens).
     */
    public void removeScreen(int id) throws ScreenIdDoesNotExistException
    {
        screenDataManipulator.deleteScreen(id);
    }

    /**
     * Add a screen to the cinema. If the screen id has already been added,
     * adding another screen with the same id will throw a ScreenIdAlreadyExistsException.
     *
     * @param screen The screen to be added.
     * @throws ScreenIdAlreadyExistsException If the id of the screen that we are trying to add is already present.
     * @throws IllegalArgumentException If the screen is null.
     */
    public void addScreen(Screen screen) throws ScreenIdAlreadyExistsException
    {
        if(screen == null) {
            throw new IllegalArgumentException("Screen cannot be null.");
        }
        addScreenToStorage(screen);
    }

    /**
     * Adds the screen to the storage, but only if the id of the screen is not matched by a screen currently stored.
     * @param screen The screen to be added to storage.
     * @throws ScreenIdAlreadyExistsException If the id of the screen that we are trying to add is already present.
     */
    private void addScreenToStorage(Screen screen) throws ScreenIdAlreadyExistsException {
        List<Screen> screens = screenDataManipulator.getAllScreens();
        if (screens.stream().anyMatch(s -> s.getId() == screen.getId())) {
            throw new ScreenIdAlreadyExistsException("Screen with id " + screen.getId() + " already exists.");
        }
        screenDataManipulator.recordScreen(screen);
    }

    /**
     * Get a screen using the provided id.
     *
     * @param id The id of the screen to get.
     * @return The screen that matches the id.
     * @throws ScreenIdDoesNotExistException If the screen was not found.
     */
    public Screen findScreen(int id) throws ScreenIdDoesNotExistException
    {
        return screenDataManipulator.getScreenById(id);
    }

    /**
     * Show a new movie at a specific screen. No movie is added if there
     * is no screen with the provided id.
     * NOTE: If screen.addNewMovie(movieTitle, ticketCost) is used instead of this method, then the screen will
     * not be updated in the storage correctly.
     *
     * @param id         The id of the screen we want to add the movie to.
     * @param movieTitle The title of the movie.
     * @param ticketCost The cost of a ticket (in cents).
     * @throws ScreenIdDoesNotExistException If the screen with the id parameter provided does not exist.
     */
    public void addNewMovie(int id, String movieTitle, int ticketCost) throws ScreenIdDoesNotExistException
    {
        // Throws ScreenIdDoesNotExistException if the screen was not found, thus validating the id.
        screenDataManipulator.getScreenById(id);

        screenDataManipulator.updateScreening(id, movieTitle, ticketCost);
    }

    /**
     * Remove the movie from a specific screen. No movie is removed if there
     * is no screen with the provided id.
     * NOTE: If screen.removeMovie() is used instead of this method, then the screen will
     * not be updated in the database correctly.
     *
     * @param id         The id of the screen we want to remove the movie from.
     * @throws ScreenIdDoesNotExistException If the screen with the id parameter provided does not exist.
     */
    public void removeMovie(int id) throws ScreenIdDoesNotExistException
    {
        // Throws ScreenIdDoesNotExistException if the screen was not found, thus validating the id.
        screenDataManipulator.getScreenById(id);

        screenDataManipulator.removeScreening(id);
    }

    /**
     * Get the details of the screens currently showing movies at the cinema, as a String.
     * @return The details of only the screens showing movies, as a String.
     */
    public String getAllMoviesDetails()
    {
        StringBuilder details = new StringBuilder();
        screenDataManipulator.getAllScreens().stream()
                .filter(Screen::hasMovieScreening)
                .map(Screen::getDetails)
                .forEach(x -> details.append("\n").append(x));
        return details.toString();
    }

    /**
     * Get the details of all screens at the cinema, as a String.
     * @return The details of all screens, as a String.
     * TODO: Do we need this method?
     */
    public String getAllScreenDetails()
    {
        StringBuilder details = new StringBuilder();
        screenDataManipulator.getAllScreens().stream()
                .map(Screen::getDetails)
                .forEach(x -> details.append("\n").append(x));
        return details.toString();
    }

    /**
     * TODO: We don't need this.
     * Book a random ticket for a movie.
     *
     * @param movieTitle The movie to book a random ticket for.
     * @return A ticket to the movie, chosen at random.
     * @throws NoAvailableSeatException   If there are no available seats for the screening of the movie.
     * @throws MovieDoesNotExistException If the movie is not being screened.
     */
    public Ticket bookRandomTicket(String movieTitle) throws NoAvailableSeatException, MovieDoesNotExistException
    {
        /*
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
         */
        return null;
    }

    /**
     * Get the Screen that is screening a movie.
     *
     * @param movie The title of the movie, or partial title of the movie, to search for.
     * @return The Screen that is screening the movie.
     * @throws MovieDoesNotExistException If there is no screening of this movie.
     */
    public Screen validateMovieTitle(String movie) throws MovieDoesNotExistException
    {
        return screenDataManipulator.getAllScreens().stream()
                .filter(Screen::hasMovieScreening)
                .filter(x -> x.getMovieTitle().toLowerCase().contains(movie.toLowerCase()))
                .findFirst().orElseThrow(() -> new MovieDoesNotExistException("Movie '" + movie + "' is not being screened."));
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
        Screen screen = validateMovieTitle(movieTitle);

        Ticket ticket = screen.bookTicket(seatNumber, rowNumber);
        // Record the screen, with one less seat available, in storage.
        screenDataManipulator.recordScreen(screen);
        return ticket;
    }
}
