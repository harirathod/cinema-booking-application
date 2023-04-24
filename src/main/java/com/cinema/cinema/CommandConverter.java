package com.cinema.cinema;

/**
 * This class provides methods for converting String types to ManagerCommandWord and Command types.
 * @author Hari Rathod
 * @version 2023.03.29
 */
public class CommandConverter {

    /**
     * Convert the string to a Command.
     * @param inputString The string to be converted to a command. For a command to recognise individual words, they
     *               must be separated by a space. This method can recognise at most 2 words, separated by a space, in an 'inputString'.
     * @return A Command for the string provided.
     */
    public static <E extends CommandWord> Command<E> convertToCommand(String inputString, E e)
    {
        // First word defines the command.
        String commandWord = null;
        // Second word defines the subject / object of the command.
        String secondWord = null;

        // Split the input string into 2 parts, and trim each part.
        String[] inputWords = inputString.trim().split(" ", 2);
        for (int i = 0; i < inputWords.length; i++) {
            inputWords[i] = inputWords[i].trim();
        }

        if(inputWords.length >= 1) {
            commandWord = inputWords[0];
            if (inputWords.length >= 2) {
                secondWord = inputWords[1];
            }
        }
        return new Command<E>(e.convertToCommandWord(commandWord), secondWord);
    }
}
