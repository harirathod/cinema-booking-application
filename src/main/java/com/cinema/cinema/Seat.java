package com.cinema.cinema;

import java.io.Serializable;

/**
 * Class Seat defines a single seat in a cinema.
 *
 * @author Hari Rathod k22002783
 * @version 2023.03.28
 */
public class Seat implements Serializable
{
    // Whether the seat is available or not.
    private boolean available;

    /**
     * Initialise fields. Seat is available by default.
     */
    public Seat()
    {
        available = true;
    }

    /**
     * Check whether a seat is available or not.
     * @return True is the seat is available, false if unavailable (i.e., booked).
     */
    public boolean isAvailable()
    {
        return available;
    }

    /**
     * Make the seat available.
     */
    public void setAvailable()
    {
        available = true;
    }

    /**
     * Make the seat unavailable (booked).
     */
    public void setUnavailable()
    {
        available = false;
    }
}
