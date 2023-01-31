import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * The test class for Command.
 * @author Hari Rathod
 * @version 2023.01.31
 */
public class CommandTest {
    public CommandTest()
    {
    }

    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    /**
     * Test that a Command is created correctly.
     */
    @Test
    public void testCommand()
    {
        Command command = new Command(CommandWord.BOOK, "Avatar");
        assertEquals(CommandWord.BOOK, command.getCommandWord());
        assertEquals("Avatar", command.getSecondWord());
    }
}
