/**
 * This class processes individual lines of user input. It converts the input from a String into a Command, and returns it.
 * @author Hari Rathod
 * @version 2023.01.31
 */

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Parser {
    private Scanner scanner;                // scanner to read input
    private CommandWordConverter commandWords;       // a list of all the command words


    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.readInput();
    }

    public Parser() {
        scanner = new Scanner(System.in);
    }

    /**
     * Read the next line of input and reformat the 'String + String + ...' line as 'CommandWord + String + ...' instead.
     * @return The next line, in the format 'CommandWord + String + String + ...' depending on how many words the Parser allows as input.
     */
    public Command readInput()
    {
        String commandWord = null;
        String secondWord = null;

        System.out.print("> ");
        String inputLine = scanner.nextLine().toLowerCase();      // process the next line

        // split the input string into 2 parts: the command, and the subject of the command
        String[] inputWords = inputLine.trim().split(" ", 2);
        for(int i = 0; i < inputWords.length; i++) {
            inputWords[i] = inputWords[i].trim();
        }
        if(inputWords.length > 0) {
            commandWord = inputWords[0];        // first word defines the command
            if(inputWords.length > 1) {
                secondWord = inputWords[1];     // second word defines the object / subject of the command
            }
        }

        return new Command(CommandWordConverter.toCommandWord(commandWord), secondWord);
    }
}
