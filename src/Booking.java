/**
 * This class is the central class that links the parsing and screening classes together. It implements the functionality of
 * commands and evaluates them, as the parser returns them.
 * @author Hari Rathod
 * @version 2023.02.05
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
     * Starts the booking, and continue to process user input until the user quits the application.
     */
    public void start()
    {
        System.out.println("Welcome to Glacier Cinema!");
        boolean finished = false;
        while(!finished) {
            finished = evaluateCommand(parser.readInput());            // while the user is not finished, get the next command and evaluate it
        }
        System.out.println("Thanks for visiting, and have a great time!");
    }

    /**
     * Evaluates the command entered. If null is entered, it evaluates the command as Command.UNKNOWN.
     * @param command The command to evaluate.
     */
    public boolean evaluateCommand(Command command)
    {
        boolean haveFinished = false;
        System.out.println(getSeparator());
        if(command == null) {
            unknown();
            return false;
        }
        switch (command.getCommandWord()) {
            case HELP -> help();
//            case BOOK -> book(command);
            case QUIT -> haveFinished = true;
            default -> unknown();
        }
        System.out.println(getSeparator());
        return haveFinished;
    }

    /**
     * Provide helpful information for the user.
     */
    private void help()
    {
        System.out.println("With our booking platform you can book movies, etc.");
        System.out.println(parser.getAllCommands());
    }


    //private void book(Command command);

    private void unknown()
    {
        System.out.println("Sorry, we didn't understand what you meant.\nPlease enter 'help' for more advice.");
    }

    /**
     * Return a line of dashes, that can be used to separate sections of text. Use to increase readability.
     * @return A line of dashes, to separate sections of text.
     */
    private String getSeparator()
    {
        return "     ----------------------------     ";
    }
}
