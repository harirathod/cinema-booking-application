import java.util.Scanner;

public class Parser {
    private Scanner scanner;                // scanner to read input


    public Parser() {
        scanner = new Scanner(System.in);
    }

    public String processInput()
    {
        String commandWord = null;
        String secondWord = null;
        if(scanner.hasNext()) {
            commandWord = scanner.next();
            if(scanner.hasNext()) {
                secondWord = scanner.next();
            }
        }
        return commandWord + " " + secondWord;
    }
}
