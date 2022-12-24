import java.util.HashSet;
import java.util.Random;
import java.time.LocalDateTime;

/**
 * Class Screen represents a screen in a multiplex cinema.
 *
 * @author Hari Rathod k22002783
 * @version 2022.12.08
 */
public class Screen
{
    private String movieTitle;
    private int ticketCost;
    private int availableSeats;
    private Seat[][] seats;
    private int id;

    /**
     * Constructor for objects of class Screen. Each row has a fixed length of
     * 20 seats.
     * If the number of seats for a screen is 5, then the screen will contain
     * 1 row, with 20 seats. If the number of seats provided is 21, the screen
     * will contain 2 rows, each with 20 seats (40 seats total).
     * @param numberOfSeats The minimum number of seats the screen should have.
     */
    public Screen(int id, int numberOfColumns, int numberOfRows)
    {
        this.id = id;
        if(numberOfColumns < 1 || numberOfRows < 1) {
            this.seats = new Seat[0][0];
            return;
        }
        seats = new Seat[numberOfColumns][numberOfRows];
        for(int i = 0; i < seats.length; i++) {
            for(int  j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat();
            }
        }
    
        emptyScreen();
    }
    
    /**
     * Empty the screen - make every seat available.
     */
    public void emptyScreen()
    {
        for(int i = 0; i < seats.length; i++) {
            for(int  j = 0; j < seats[i].length; j++) {
                seats[i][j].setAvailable();
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
        this.ticketCost = ticketCost;
        emptyScreen();
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
        details += "Ticket Cost: " + ticketCost + "\n";
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
    /*
     * Get a best ticket (a ticket closest to the centre of the room).
     * @return A ticket closest to the centre of the room.
     */
    /*public Ticket getBestTicket()
    {
        
    }*/
    
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
     * Get the cost (in cents) of a ticket for the movie.
     * @return The cost of a ticket to see the movie.
     */
    public int getTicketCost()
    {
        return ticketCost;
    }
}
