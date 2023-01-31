/**
 * This class is used as an intermediary,
 */

public class CommandWordConverter {

    public static CommandWord toCommandWord(String word)
    {
        for(CommandWord command : CommandWord.values()) {
            if(word != null && word.equals(command.toString())) {
                return command;
            }
        }
        return CommandWord.UNKNOWN;
    }
}
