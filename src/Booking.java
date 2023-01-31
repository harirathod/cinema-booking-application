/**
 * This class is the central class that links the parsing and screening classes together. It creates the parser and ticket office,
 * and defines the functionality of command words with respect to the ticket office.
 * @author Hari Rathod
 * @version 2023.01.31
 */

public class Booking {
    private Parser parser;
    private TicketOffice office;

    /**
     * Constructor to initialise fields.
     */
    public Booking()
    {
        parser = new Parser();
        office = new TicketOffice();
    }

    /**
     *
     */
    public void start()
    {

    }

    private void help();
    private void quit();
    private void book(Command command);
}
