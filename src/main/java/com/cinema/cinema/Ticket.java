package com.cinema.cinema;

import java.time.LocalDateTime;

    /**
     * Ticket defines a ticket that is associated with the showing of a movie
     * at a specific screen.
     *
     * @author Hari Rathod
     * @version 2023.03.28
     */
    public class Ticket
    {
        // The screen id in which the movie is being shown.
        private int screenId;
        private String movieTitle;
        private int rowNumber;
        // Column number is the same as the seat number.
        private int seatNumber;
        private int cost;
        private LocalDateTime date;

        /**
         * Initialise fields.
         */
        public Ticket(int screenId, String movieTitle, int seatNumber, int rowNumber,
                      int cost, LocalDateTime date)
        {
            this.screenId = screenId;
            this.movieTitle = movieTitle;
            this.rowNumber = rowNumber;
            this.seatNumber = seatNumber;
            this.cost = cost;
            this.date = date;
        }

        /**
         * Return the details of the ticket, as a formatted string.
         * @return The details of the ticket, as a formatted string.
         */
        public String getDetails()
        {
            // Line of dashes to mark edges of the ticket.
            String dashes = "==============================";

            String details = "";
            details += "Screen ID: " + screenId + "\n"
                    + "Movie Title: " + movieTitle + "\n"
                    + "Row Number: " + rowNumber + "\n"
                    + "Seat Number: " + seatNumber + "\n"
                    + "Cost: " + cost + "\n"
                    + "Date: " + date;

            return dashes + "\n" + details + "\n" + dashes;
        }

        /**
         * Get the name of the movie the ticket has been booked for.
         * @return The name of the movie.
         */
        public String getMovieTitle()
        {
            return movieTitle;
        }

        /**
         * Get the row number the ticket has been booked for.
         * @return The row number.
         */
        public int getRowNumber()
        {
            return rowNumber;
        }

        /**
         * Get the seat number the ticket has been booked for.
         * @return The seat number.
         */
        public int getSeatNumber()
        {
            return seatNumber;
        }
    }

