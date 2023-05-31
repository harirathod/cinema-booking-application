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
    Screen screen1;
    Screen screen2;

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
        screen1 = new Screen(1, 10, 20);
        screen2 = new Screen(2, 3, 5);
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
        try {
            screen1.book(1, 1);
            assertTrue(true);
            screen1.book(1, 1);
            fail();
        } catch (UnavailableSeatException e) {
            assertTrue(true);
        }

        try {
            screen1.book(2,2);
        } catch (UnavailableSeatException e) {
            fail();
        }
    }

    /**
     * Test 'get number of seats'.
     */
    @Test
    public void testGetNumberOfSeats()
    {
        assertEquals(200, screen1.getNumberOfSeats());
        assertEquals(15, screen2.getNumberOfSeats());
    }

    /**
     * Test 'get id'.
     */
    @Test
    public void testGetId()
    {
        assertEquals(1, screen1.getId());
        assertEquals(2, screen2.getId());
    }

    /**
     * Test if the available seats are calculated correctly.
     */
    @Test
    public void testGetNumberOfAvailableSeats()
    {
        try {
            screen1.book(1, 2);
            assertEquals(199, screen1.getNumberOfAvailableSeats());
        } catch (UnavailableSeatException e) {
            fail();
        }


        try {
            screen2.book(-1, 3);
            fail();
        } catch (ArrayIndexOutOfBoundsException | UnavailableSeatException e) {
            assertEquals(15, screen2.getNumberOfAvailableSeats());
        }

    }

    /**
     * Test if a new movie title is added correctly.
     */
    @Test
    public void testAddNewMovie()
    {
        screen1.addNewMovie("Shazam 3", 900);
        assertEquals("Shazam 3", screen1.getMovieTitle());
        assertEquals(900, screen1.getTicketCost());

        screen2.addNewMovie("Black Panther: Return of the Jedi", 1300);
        assertEquals("Black Panther: Return of the Jedi", screen2.getMovieTitle());
        assertEquals(1300, screen2.getTicketCost());
    }

    /**
     * Test that a movie is correctly removed from a screen.
     */
    @Test
    public void testRemoveMovie()
    {
        assertFalse(screen1.hasMovieScreening());
        assertNull(screen1.getMovieTitle());
        assertEquals(0, screen1.getTicketCost());

        screen1.addNewMovie("Shazam | Light of Thunder", 2700);
        assertTrue(screen1.hasMovieScreening());
        assertNotNull(screen1.getMovieTitle());
        assertEquals(2700, screen1.getTicketCost());

        screen1.removeMovie();
        assertFalse(screen1.hasMovieScreening());
        assertNull(screen1.getMovieTitle());
        assertEquals(0, screen1.getTicketCost());


    }

    /**
     * Test if the empty screen method works correctly (should free up all seats).
     */
    @Test
    public void testEmptyScreen()
    {

        try {
            screen1.book(1,1);
            screen1.book(6,2);
            screen1.book(7,10);
        } catch (UnavailableSeatException e) {
            fail();
        }
        assertEquals(200, screen1.getNumberOfSeats());
        assertEquals(197, screen1.getNumberOfAvailableSeats());
        screen1.emptyScreen();
        assertEquals(200, screen1.getNumberOfAvailableSeats());
    }
}



