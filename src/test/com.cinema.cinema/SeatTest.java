package com.cinema.cinema;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SeatTest.
 *
 * @author  Hari Rathod
 * @version 2022.12.24
 */
public class SeatTest
{
    Seat seat;

    /**
     * Default constructor for test class SeatTest
     */
    public SeatTest()
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
        seat = new Seat();
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
     * Test if the availability updates correctly.
     */
    @Test
    public void testAvailability()
    {
        assertTrue(seat.isAvailable());
        seat.setUnavailable();
        assertFalse(seat.isAvailable());
        seat.setAvailable();
        assertTrue(seat.isAvailable());
    }
}
