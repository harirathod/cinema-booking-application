package com.cinema.cinema;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * The test class ScreenTest.
 *
 * @author  Hari Rathod k22002783
 * @version 2022.12.08
 */
public class ScreenTest
{
    Screen screen1 = new Screen(0, 0, 0);
    Screen screen2 = new Screen(-1, 0, 0);
    Screen screen3 = new Screen(1, 20, 1);
    Screen screen4 = new Screen(2, 30, 2);

    /**
     * Default constructor for test class ScreenTest
     */
    public ScreenTest()
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
     * Test if the book method works correctly.
     */
    @Test
    public void testBook()
    {
        assertEquals(false, screen1.book(0, 0));
        assertEquals(true, screen3.book(15,1));
        assertEquals(true, screen4.book(23,2));
    }

    /**
     * Test 'get number of seats'.
     */
    @Test
    public void testGetNumberOfSeats()
    {
        assertEquals(0, screen1.getNumberOfAvailableSeats());
        assertEquals(0, screen2.getNumberOfAvailableSeats());
        assertEquals(20, screen3.getNumberOfAvailableSeats());
        assertEquals(60, screen4.getNumberOfAvailableSeats());
    }

    /**
     * Test 'get id'.
     */
    @Test
    public void testGetId()
    {
        assertEquals(0, screen1.getId());
        assertEquals(-1, screen2.getId());
    }

    /**
     * Test if the available seats are calculated correctly.
     */
    @Test
    public void testGetNumberOfAvailableSeats()
    {
        screen4.book(19, 2);
        assertEquals(59, screen4.getNumberOfAvailableSeats());

        screen3.book(-1, 3);
        assertEquals(20, screen3.getNumberOfAvailableSeats());
    }

    /**
     * Test if a new movie title is added correctly.
     */
    @Test
    public void testAddNewMovie()
    {
        screen4.addNewMovie("Shazam 3", 900);
        assertEquals("Shazam 3", screen4.getMovieTitle());
        assertEquals(900, screen4.getTicketCost());

        screen3.addNewMovie("Black Panther: Return of the Jedi", 1300);
        assertEquals("Black Panther: Return of the Jedi", screen3.getMovieTitle());
        assertEquals(1300, screen3.getTicketCost());
    }

    /**
     * Test that a movie is correctly removed from a screen.
     */
    @Test
    public void testRemoveMovie()
    {
        assertFalse(screen3.hasMovieScreening());
        assertNull(screen3.getMovieTitle());
        assertEquals(0, screen3.getTicketCost());

        screen3.addNewMovie("Shazam | Light of Thunder", 2700);
        assertTrue(screen3.hasMovieScreening());
        assertNotNull(screen3.getMovieTitle());
        assertEquals(2700, screen3.getTicketCost());

        screen3.removeMovie();
        assertFalse(screen3.hasMovieScreening());
        assertNull(screen3.getMovieTitle());
        assertEquals(0, screen3.getTicketCost());
    }

    /**
     * Test if the empty screen method works correctly (should free up all seats).
     */
    @Test
    public void testEmptyScreen()
    {
        screen4.book(1,1);
        screen4.book(6,2);
        screen4.book(13,1);
        assertEquals(60, screen4.getNumberOfSeats());
        assertEquals(57, screen4.getNumberOfAvailableSeats());
        screen4.emptyScreen();
        assertEquals(60, screen4.getNumberOfAvailableSeats());
    }
}



