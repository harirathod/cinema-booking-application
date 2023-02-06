import java.util.List;

/**
 * This enumeration class defines the command words available.
 * @author Hari Rathod
 * @version 2023.01.31
 */

public enum CommandWord {
    HELP("help"), QUIT("quit"), BOOK("book"), LIST("list"), UNKNOWN(null);

    private String commandString;
    private String placeholder;
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


    private void addPlaceholdersToEnum()
    {
        HELP.addPlaceholder(null);
        QUIT.addPlaceholder(null);
        BOOK.addPlaceholder("<movie title>");
        LIST.addPlaceholder(null);
        UNKNOWN.addPlaceholder(null);

    }
    private void addPlaceholder(String placeholder)
    {
        this.placeholder = placeholder;
    }

}
