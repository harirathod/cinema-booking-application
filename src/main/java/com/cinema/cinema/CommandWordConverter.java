package com.cinema.cinema;

/**
 * This class is used as an intermediary class, to convert the first word from a String to a main.cinema.CommandWord.
 * @author Hari Rathod
 * @version 2023.01.31
 */
public class CommandWordConverter {

    /**
     * Converts the inputted word to an appropriate command word. If the word is null or unrecognised, the main.cinema.CommandWord returned
     * is main.cinema.CommandWord.UNKNOWN.
     * @param word The word to convert to a main.cinema.CommandWord.
     * @return The main.cinema.CommandWord most appropriate to the word parameter entered.
     */
    public static CommandWord toCommandWord(String word)
    {
        if(word == null) {
            return CommandWord.UNKNOWN;
        }
        word = word.toLowerCase();
        for(CommandWord command : CommandWord.values()) {
            if(word.equals(command.toString())) {
                return command;
            }
        }
        return CommandWord.UNKNOWN;
    }
}
