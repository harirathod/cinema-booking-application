package com.cinema.cinema;

import java.util.regex.Pattern;

/**
 * This class is used to check whether input represents certain types.
 * @author hari_rathod
 * @version 2023.04.25
 */
public class NumberMatcher {

    /**
     * Check whether a string represents a single integer, such as 9 or 108.
     * @param string The input string to be checked.
     * @return True if the string represents a single integer, false otherwise.
     */
    public static boolean matchesSingleInteger(String string)
    {
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(string).matches();
    }
}
