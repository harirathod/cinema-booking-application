package com.cinema.cinema;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class main.cinema.TicketOffice is manages the other class. It is through this class
 * that screens and movies can be added, and tickets can be bought.
 *
 * @author Hari Rathod
 * @version 2022.12.08
 */
public class TicketOffice
{

    private Set<Screen> screens;
    /**
     * Constructor for objects of class main.cinema.TicketOffice.
     */
    public TicketOffice()
    {
        screens = new HashSet<>();
    }

    /**
     * Add a screen to the cinema. If the number of columns or row for the
     * screen is invalid (< 0), no screen will be added. If the screen already
     * exists, another screen cannot be added.
     * @param id The id of the screen. Must be a positive number.
     * @param numberOfColumns The number of columns for the screen. Must be a positive number.
     * @param numberOfRows The number of rows for the screen. Must be a positive number.
     * @return True if the screen was successfully added, else false.
     */
    public boolean addScreen(int id, int numberOfColumns, int numberOfRows)
    {
        if (numberOfColumns < 1 || numberOfRows < 1 || id == 0) {
            return false;
        }
        for (Screen screen : screens) {
            if(screen.getId() == id) {
                return false;
            }
        }
        screens.add(new Screen(id, numberOfColumns, numberOfRows));
        return true;
    }

    /**
     * Remove screen from the cinema. If the screen is not in the cinema, nothing will happen.
     * @param id The id of the screen to be removed.
     * @return True if the screen was successfully removed, false otherwise.
     */
    public boolean removeScreen(int id)
    {
        Iterator<Screen> it = screens.iterator();
        while(it.hasNext()) {
            if(it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Add a screen to the cinema. If the screen id has already been added,
     * adding another screen with the same id will have no effect.
     * @param screen The screen to be added.
     * @return True if the screen was successfully added.
     */
    public boolean addScreen(Screen screen)
    {
        if(screen == null) {
            return false;
        }
        Iterator<Screen> it = screens.iterator();
        while(it.hasNext()) {
            if(it.next().getId() == screen.getId()) {
                return false;
            }
        }
        screens.add(screen);
        return true;
    }

    /**
     * Get a screen using the provided id.
     * @param id The id of the screen that we want.
     * @return The screen that matches the id, or null if not found.
     */
    public Screen findScreen(int id)
    {
        return screens.stream()
                .filter(screen -> screen.getId() == id)
                .findFirst().orElse(null);
    }

    /**
     * Show a new movie at a specific screen. Does not add the movie if there
     * is no screen as the provided id.
     * @param id The id of the screen we want to change the movie showing.
     * @param movieTitle The title of the movie we want the screen to show.
     * @param ticketCost The cost of a ticket in cents.
     * @return True if a new movie was successfully added, false if not.
     */
    public boolean addNewMovie(int id, String movieTitle, int ticketCost)
    {
        Screen screen = screens.stream()
                .filter(x -> x.getId() == id)
                .findFirst().orElse(null);

        if(screen == null) return false;
        else screen.addNewMovie(movieTitle, ticketCost);

        return true;
    }

    /**
     * Show all the movies currently showing at the cinema.
     */
    public void showMovies()
    {
        screens.stream()
                .filter(Screen::hasMovieScreening)
                .map(Screen::getDetails)
                .forEach(System.out::println);
    }

    /**
     * Book a random ticket for a movie.
     * @param movieTitle The movie to book a random ticket for.
     * @return A ticket to the movie, chosen at random.
     */
    public Ticket bookRandomTicket(String movieTitle)
    {
        return screens.stream()
                .filter(screen -> movieTitle.equals(screen.getMovieTitle()))
                .map(Screen::getRandomTicket)
                .findFirst().orElse(null);
    }

    /**
     * Book a ticket for a movie.
     * @param movieTitle The movie to book a ticket for.
     * @param seatNumber The seat number, i.e., how far across in a given row (the column number).
     * @param rowNumber The row number, i.e., how close to the screen.
     * @return A ticket to the movie, chosen at random.
     */
    public Ticket bookTicket(String movieTitle, int seatNumber, int rowNumber)
    {
        return screens.stream()
                .filter(screen -> movieTitle.equals(screen.getMovieTitle()))
                .map(screen -> screen.getTicket(seatNumber, rowNumber))
                .findFirst().orElse(null);
    }

    /**
     * Get a collection of all movies available.
     */
    public Set<String> getAllMovieTitles()
    {
        return screens.stream()
                .map(Screen::getMovieTitle)
                .collect(Collectors.toSet());
    }
}
