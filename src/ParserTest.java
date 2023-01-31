import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * The test class ParserTest. Test the Parser functionality.
 *
 * @author  Hari Rathod k22002783
 * @version 2023.01.31
 */
public class ParserTest {
    public ParserTest()
    {
    }

    /**
     * The operations to be executed before each test case.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * The operations to be executed after each test case.
     */
    @AfterEach
    public void tearDown()
    {
    }

    /**
     * Test if the input is read and processed correctly.
     */

    public void testReadInput()
    {
        Parser parser = new Parser();
        Command command = parser.readInput();
        assertSame(CommandWord.BOOK, command.getCommandWord());
        assertEquals("black panther 2", command.getSecondWord());
    }
}
