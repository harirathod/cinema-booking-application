package com.cinema.cinema;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TicketOfficeTest.
 * TODO: Tests do not work as of now, due to integration with database
 *  in progress.
 *
 * @author  Hari Rathod
 * @version 2022.12.24
 */
public class TicketOfficeTest
{
    TicketOffice ticketOffice;
    Screen screen1;
    Screen screen2;

    /**
     * Default constructor for test class TicketOfficeTest
     */
    public TicketOfficeTest()
    {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case.
     */
    @BeforeEach
    public void setUp()
    {
        ticketOffice = new TicketOffice();
        screen1 = new Screen(1, 13, 12);
        screen2 = new Screen(2, 31, 5);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case.
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
        try {
            ticketOffice.addScreen(1, 21, 13);
            assertTrue(true);
        } catch (ScreenIdAlreadyExistsException | InvalidScreenParameterException e) {
            fail();
        }
        try {
            ticketOffice.findScreen(1);
            assertTrue(true);
        } catch (ScreenIdDoesNotExistException e) {
            fail();
        }
    }

    /**
     * Test if adding a movie to a screen works correctly.
     */
    @Test
    public void testAddMovie()
    {
        try {
            ticketOffice.addNewMovie(-1, "", 0);
            fail();
        }
        catch (ScreenIdDoesNotExistException e) {
            assertTrue(true);
        }

        try {
            ticketOffice.addScreen(1, 10, 10);
            ticketOffice.addNewMovie(1, "Whoo duhn-knit?", 1000);
            assertTrue(true);
        } catch (ScreenIdAlreadyExistsException | InvalidScreenParameterException | ScreenIdDoesNotExistException e) {
            fail();
        }
    }

    /**
     * Test if the add screen method works correctly.
     */
    @Test
    public void testAddScreen()
    {
        try {
            ticketOffice.addScreen(null);
            fail();
        }
        catch (IllegalArgumentException | ScreenIdAlreadyExistsException e) {
            assertTrue(true);
        }

        try {
            ticketOffice.addScreen(1, 10, 10);
            assertTrue(true);
            ticketOffice.addScreen(1, 30, 12);
            fail();
        } catch (ScreenIdAlreadyExistsException | InvalidScreenParameterException e) {
            assertTrue(true);
        }
    }

    /**
     * Test if a ticket is correctly booked.
     */
    @Test
    public void testBookTicket()
    {
        Ticket ticket = null;
        try {
            ticketOffice.addScreen(1, 12, 12);
            ticketOffice.addNewMovie(1, "Trans-4-mers: Light of the Sun", 1700);
            ticket = ticketOffice.bookTicket("Trans-4-mers: Light of the Sun", 10, 3);
        } catch (Exception e) {
            fail();
        }

        assertEquals(ticket.getMovieTitle(), "Trans-4-mers: Light of the Sun");
        assertEquals(ticket.getSeatNumber(), 10);
        assertEquals(ticket.getRowNumber(), 3);
    }

    /**
     * Test if a screen is correctly removed.
     */
    @Test
    public void testRemoveScreen()
    {
        try {
            ticketOffice.addScreen(3, 31, 26);
        } catch (Exception e) {
            fail();
        }
        try {
            ticketOffice.addScreen(3, 10, 10);
            fail();
        } catch (ScreenIdAlreadyExistsException | InvalidScreenParameterException e) {
            try {
                ticketOffice.removeScreen(3);
            } catch (ScreenIdDoesNotExistException ex) {
                fail();
            }
        }

        try {
            ticketOffice.addScreen(3, 10, 10);
        } catch (InvalidScreenParameterException | ScreenIdAlreadyExistsException ex) {
            fail();
        }
    }
}
