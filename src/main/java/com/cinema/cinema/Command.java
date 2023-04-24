package com.cinema.cinema;
/**
 * This class defines the format for a Command, consisting of a command (first) word and a second word.
 * The command (first) word defines the action to be done, e.g., 'BOOK', and the second word defines the object upon which to do the action, e.g.,
 * 'movie'. This gives a Command as 'BOOK movie'.
 * @author Hari Rathod
 * @version 2023.03.29
 */
public class Command<E extends CommandWord> {
    private E commandWord;
    private String secondWord;

    /**
     * Initialise fields
     * @param commandWord The first word of the Command. The 'action'.
     * @param secondWord The second word of the Command. The 'subject'.
     */
    public Command(E commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Get the command word.
     * @return The command word.
     */
    public E getCommandWord()
    {
        return commandWord;
    }

    /**
     * Get the second word.
     * @return The second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * Check if the command has a second word.
     * @return True if the command has a second word. False if it doesn't have a second word or if the second word is null.
     */
    public boolean hasSecondWord()
    {
        return !(secondWord == null || secondWord.isBlank());
    }
}
