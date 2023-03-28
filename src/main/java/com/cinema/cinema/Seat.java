package com.cinema.cinema;

/**
 * Class main.cinema.Seat defines a single seat in a cinema.
 *
 * @author Hari Rathod k22002783
 * @version 2022.12.08
 */
public class Seat
{
    private boolean available;

    /**
     * Constructor for objects of class main.cinema.Seat
     */
    public Seat()
    {
        available = true;
    }

    /**
     * Check whether a seat is available or not.
     * @return True is the seat is available, false if unavailable (booked).
     */
    public boolean isAvailable()
    {
        return available;
    }

    /**
     * Change the seat to available.
     */
    public void setAvailable()
    {
        available = true;
    }

    /**
     * Change the seat to unavailable (booked).
     */
    public void setUnavailable()
    {
        available = false;
    }
}
