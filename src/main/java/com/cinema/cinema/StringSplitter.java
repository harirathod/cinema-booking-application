package com.cinema.cinema;

import java.util.regex.Pattern;

/**
 * Utility class that has methods to split strings.
 * @author hari_rathod
 * @version 2023.04.14
 */
public class StringSplitter {

    /**
     * Convert a String to an array of Strings, split by punctuation. E.g., "3, 4hi!'; 55" will return [3, 4hi, 55]
     * @param string The String to be converted to an array.
     * @return An array of Strings, split by punctuation.
     */
    public static String[] splitByPunctuation(String string)
    {
        return Pattern.compile("[\\p{Punct}\\s]+").split(string);
    }
}
