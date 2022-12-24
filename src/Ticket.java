import java.util.Date;
import java.time.LocalDateTime;

/**
 * Class Ticket defines a ticket that is associated with the showing of a movie
 * at a specific screen.
 *
 * @author Hari Rathod k22002783
 * @version 2022.12.10
 */
public class Ticket
{
    private int screenId;    // the screen id in which the movie is being shown
    private String movieTitle;
    private int rowNumber;
    private int seatNumber;   // column number = seat number
    private int cost;
    private LocalDateTime date;
    /**
     * Constructor for objects of class Ticket
     */
    public Ticket(int screenId, String movieTitle, int seatNumber, int rowNumber,
    int cost, LocalDateTime date)
    {
        this.screenId = screenId;
        this.movieTitle = movieTitle;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.cost = cost;
        this.date = date;
    }
    
    /**
     * Print the details of a ticket.
     */
    public void printDetails()
    {
        String details = "";
        details += "Screen ID: " + screenId + "\n"
        + "Movie Title: " + movieTitle + "\n"
        + "Row Number: " + rowNumber + "\n"
        + "Seat Number: " + seatNumber + "\n"
        + "Cost: " + cost + "\n"
        + "Date: " + date + "\n";
        System.out.println(details);
    }
    
    /**
     * Get the name of the movie the ticket has been booked for.
     * @return The name of the movie.
     */
    public String getMovieTitle()
    {
        return movieTitle;
    }
    
    /**
     * Get the row number the ticket has been booked for.
     * @return The row number.
     */
    public int getRowNumber()
    {
        return rowNumber;
    }
    
    /**
     * Get the seat number the ticket has been booked for.
     * @return The seat number.
     */
    public int getSeatNumber()
    {
        return seatNumber;
    }
}
