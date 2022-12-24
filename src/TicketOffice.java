import java.util.HashMap;

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
     * @param id The id of the screen.
     * @param numberOfColumns The number of columns for the screen.
     * @param numberOfRows The number of rows for the screen.
     * @return True if the screen was successfully added, else false.
     */
    public boolean addScreen(int id, int numberOfColumns, int numberOfRows)
    {
        if(numberOfColumns < 0 || numberOfRows < 0 || screens.get(id) != null) {
            return false;
        }
        screens.put(id, new Screen(id, numberOfColumns, numberOfRows));
        return true;
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
     * @param The movie to book a random ticket for.
     * @return A ticket to the movie, chosen at random.
     */
    public Ticket bookRandomTicket(String movieTitle)
    {
        return screens.keySet().stream()
                    .map(id -> screens.get(id))
                    .filter(screen -> movieTitle.equals(screen.getMovieTitle()))
                    .map(screen -> screen.getRandomTicket())
                    .findFirst()
                    .orElse(null);
    }
    
    /**
     * Book a ticket for a movie.
     * @param The movie to book a ticket for.
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
}
