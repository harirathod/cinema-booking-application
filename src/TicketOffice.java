import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class TicketOffice is manages the other class. It is through this class
 * that screens and movies can be added, and tickets can be bought.
 *
 * @author Hari Rathod
 * @version 2022.12.08
 */
public class TicketOffice
{

    private HashMap<Integer, Screen> screens;
    /**
     * Constructor for objects of class TicketOffice.
     */
    public TicketOffice()
    {
        screens = new HashMap<>();
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
        if(numberOfColumns < 1 || numberOfRows < 1 || screens.get(id) != null || id == 0) {
            return false;
        }
        screens.put(id, new Screen(id, numberOfColumns, numberOfRows));
        return true;
    }

    /**
     * Remove screen from the cinema. If the screen is not in the cinema, nothing will happen.
     * @param id The id of the screen to be removed.
     */
    public boolean removeScreen(int id)
    {
        for(Screen screen : screens.values()) {
            if(id == screen.getId()) {
                screens.remove(1);
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
        if(screen == null || screens.get(screen.getId()) != null) {
            return false;
        }
        screens.put(screen.getId(), screen);
        return true;
    }

    /**
     * Get a screen using the provided id.
     * @param The id of the screen that we want.
     * @return The screen that matches the id, or null if not found.
     */
    public Screen findScreen(int id)
    {
        return screens.get(id);
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
        if(screens.get(id) != null) {
            screens.get(id).addNewMovie(movieTitle, ticketCost);
            return true;
        }
        return false;
    }

    /**
     * Show all the movies currently showing at the cinema.
     */
    public void showMovies()
    {
        screens.keySet().stream()
                .map(id -> screens.get(id).getDetails())
                .forEach(System.out::println);
    }

    /**
     * Book a random ticket for a movie.
     * @param movieTitle The movie to book a random ticket for.
     * @return A ticket to the movie, chosen at random.
     */
    public Ticket bookRandomTicket(String movieTitle)
    {
        return screens.keySet().stream()
                .map(id -> screens.get(id))
                .filter(screen -> movieTitle.equals(screen.getMovieTitle()))
                .map(Screen::getRandomTicket)
                .findFirst()
                .orElse(null);
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
        return screens.keySet().stream()
                .map(id -> screens.get(id))
                .filter(screen -> movieTitle.equals(screen.getMovieTitle()))
                .map(screen -> screen.getTicket(seatNumber, rowNumber))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a collection of all movies available.
     */
    public Set<String> getAllMovieTitles()
    {
        Set<String> allMovieTitles  = new HashSet<>();
        for(Screen screen : screens.values()) {
            allMovieTitles.add(screen.getMovieTitle());
        }
        return allMovieTitles;
    }
}
