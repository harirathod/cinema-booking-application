package com.cinema.cinema;

public class Movie {
    private String movieTitle;
    private int cost;

    /**
     * Initialise fields.
     * @param movieTitle The title of the movie.
     * @param cost The cost of the movie, in cents.
     */
    public Movie(String movieTitle, int cost)
    {
        this.movieTitle = movieTitle;
        this.cost = cost;
    }

    /**
     * Get the title of the movie.
     */
    public String getMovieTitle()
    {
        return movieTitle;
    }

    /**
     * Get the cost of a ticket to the movie, in cents.
     */
    public int getCost()
    {
        return cost;
    }

}
