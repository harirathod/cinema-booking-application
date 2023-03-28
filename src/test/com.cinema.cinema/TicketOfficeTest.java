package com.cinema.cinema;

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
    Screen screen1 = new Screen(1, 13, 12);
    Screen screen2 = new Screen(2, 31, 5);

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
     * Test if the correct screen is found.
     */
    @Test
    public void testFindScreen()
    {
        ticketOffice.addScreen(1, 21, 13);

        ticketOffice.addScreen(screen2);
        assertEquals(screen2, ticketOffice.findScreen(2));

        Screen screen3 = new Screen(2, 13, 2);
        ticketOffice.addScreen(screen2);
        assertEquals(screen2, ticketOffice.findScreen(2));
    }

    /**
     * Test if adding a movie to a screen works correctly.
     */
    @Test
    public void testAddMovie()
    {
        assertEquals(false, ticketOffice.addNewMovie(1, "Who?", 900));

        ticketOffice.addScreen(1, 10, 10);
        assertEquals(true, ticketOffice.addNewMovie(1, "Where?", 1000));
    }

    /**
     * Test if the add screen method works correctly.
     */
    @Test
    public void testAddScreen()
    {
        assertEquals(false, ticketOffice.addScreen(null));
        assertEquals(true, ticketOffice.addScreen(1, 21, 13));
        assertEquals(false, ticketOffice.addScreen(1, 0, 13));
        assertEquals(false, ticketOffice.addScreen(2, 0, 13));

        assertEquals(false, ticketOffice.addScreen(screen1));
        assertEquals(true, ticketOffice.addScreen(screen2));
    }

    /**
     * Test if a ticket is correctly booked.
     */
    @Test
    public void testBookTicket()
    {
        screen1.addNewMovie("Batman: Dark of the Moon", 2600);
        ticketOffice.addScreen(screen1);
        Ticket ticket = ticketOffice.bookTicket("Batman: Dark of the Moon", 7, 6);
        assertEquals(ticket.getMovieTitle(), "Batman: Dark of the Moon");
        assertEquals(ticket.getSeatNumber(), 7);
        assertEquals(ticket.getRowNumber(), 6);
    }

    /**
     * Test if a screen is correctly removed.
     */
    @Test
    public void testRemoveScreen()
    {
        assertNotNull(screen1);
        assertNull(ticketOffice.findScreen(1));
        ticketOffice.addScreen(screen1);
        assertSame(ticketOffice.findScreen(1), screen1);

        assertTrue(ticketOffice.removeScreen(1));
        assertFalse(ticketOffice.removeScreen(1));

        assertNull(ticketOffice.findScreen(1));
    }
}
