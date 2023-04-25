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
    public static Command convertToCommand(String inputString, BookingType bookingType)
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
        return new Command(convertToCommandWord(commandWord, bookingType), secondWord);
    }

    /**
     * Converts the string to an appropriate command word. If the string is null or unrecognised, the CommandWord returned
     * is CommandWord.UNKNOWN.
     * @param word The string to convert to a CommandWord.
     *             If the string is null, the CommandWord returned is CommandWord.UNKNOWN.
     * @param bookingType The booking type to get the commands for.
     * @return The CommandWord most appropriate to the parameter entered.
     */
    private static CommandWord convertToCommandWord(String word, BookingType bookingType)
    {
        if(word == null) {
            return CommandWord.UNKNOWN;
        }
        word = word.toLowerCase();
        for(CommandWord command : bookingType.getCommands()) {
            if(word.equals(command.getCommandString())) {
                return command;
            }
        }
        return CommandWord.UNKNOWN;
    }
}
