/**
 * This enumeration class defines the command words available.
 * @author Hari Rathod
 * @version 2023.01.31
 */

public enum CommandWord {
    HELP("help"), QUIT("quit"), BOOK("book"), UNKNOWN(null);

    private String commandString;
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * Get the command string associated with a command word.
     * @return The command string associated with a command word.
     */
    public String toString()
    {
        return commandString;
    }

}
