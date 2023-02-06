import java.util.List;

/**
 * This enumeration class defines the command words available.
 * @author Hari Rathod
 * @version 2023.01.31
 */

public enum CommandWord {
    HELP("help", null), QUIT("quit", null), BOOK("book", "<movie title>"),
    LIST("list", null), BASKET("basket", null),UNKNOWN(null, null);

    private String commandString;
    private String placeholder;
    CommandWord(String commandString, String placeholder)
    {

        this.commandString = commandString;
        this.placeholder = placeholder;
    }

    /**
     * Get the command string associated with a command word.
     * @return The command string associated with a command word.
     */
    public String toString()
    {
        return commandString;
    }

    /**
     * Get the placeholder associated with an enum constant.
     */
    public String getPlaceholder()
    {
        return placeholder;
    }

}
