/**
 * This class processes individual lines of user input. It converts the input from a String into a Command, and returns it.
 * @author Hari Rathod
 * @version 2023.01.31
 */

import java.util.Scanner;

public class Parser {
    private Scanner scanner;                // scanner to read input
    private CommandWordConverter commandWords;       // a list of all the command words


    public static void main(String[] args) {
        Parser parser = new Parser();
        System.out.println(parser.readInput());
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
        System.out.print("> ");
        String input = scanner.nextLine();      // process the next line
        input = input.toLowerCase();
        Scanner readInput = new Scanner(input);
        String commandWord = null;              // first word defines the command
        String secondWord = null;               // second word defines the object / subject of the command
        if(readInput.hasNext()) {
            commandWord = readInput.next();
            if(readInput.hasNext()) {
                secondWord = readInput.next();
            }
        }
        return new Command(CommandWordConverter.toCommandWord(commandWord), secondWord);
    }
}
