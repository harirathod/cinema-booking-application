
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TicketOfficeTest.
 *
 * @author  Hari Rathod 
 * @version 2022.12.24
 */
public class TicketOfficeTest
{
    TicketOffice ticketOffice = new TicketOffice();
    Screen screen = new Screen(0, 10, 26);
    Screen screen2 = new Screen(1, 12, 32);
    
    /**
     * Default constructor for test class TicketOfficeTest
     */
    public TicketOfficeTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        ticketOffice.addScreen(screen);
        ticketOffice.addScreen(screen2);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    /**
     * Test the find screen method.
     */
    @Test
    public void testFindScreen()
    {
        assertEquals(screen,ticketOffice.findScreen(0));
        assertEquals(screen2, ticketOffice.findScreen(1));
        assertEquals(null, ticketOffice.findScreen(2));
    }
}
