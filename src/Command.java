/**
 * This class defines the format for a complete command, consisting of a command word and a second word. The command word
 * defines the action to be done, e.g., 'BOOK', and the second word defines the object upon which to do the action, e.g.,
 * 'movie'. This gives a complete command as 'BOOK movie'.
 * @author Hari Rathod
 * @version 2023.01.31
 */
public class Command {
    private CommandWord commandWord;
    private String secondWord;

    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Get the command word (the first word) associated with a complete command.
     * @return The command word (the first word).
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * Get the second word (the subject of the command) associated with a complete command.
     * @return The second word (the subject of the command).
     */
    public String getSecondWord()
    {
        return secondWord;
    }
}
