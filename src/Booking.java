/**
 * This class is the central class that links the parsing and screening classes together. It implements the functionality of
 * commands and evaluates them, as the parser returns them.
 * @author Hari Rathod
 * @version 2023.02.01
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
     * Start the booking, and continue to process user input until the user quits the application.
     */
    public void start()
    {
        System.out.println("Welcome to Glacier Cinema!");
        boolean notFinished = true;
        while(notFinished) {
            evaluateCommand(parser.readInput());            // while the user is not finished, get the next command and evaluate it
        }
        System.out.println("Thanks for visiting!");
    }

    public void evaluateCommand(Command command);
    private void help();
    private void quit();
    private void book(Command command);
}
