import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ParserTest {
    Parser parser = new Parser();

    public ParserTest()
    {
        // nothing to do here
    }

    @BeforeEach
    public void setUp()
    {
        // nothing to do here
    }

    @AfterEach
    public void tearDown()
    {
        // nothing to do here
    }

    /**
     * Test that all commands are printed out correctly.
     */
    @Test
    public void testGetAllCommands()
    {
        assertEquals("Commands: help, quit, book, ", parser.getAllCommands());
    }
}
