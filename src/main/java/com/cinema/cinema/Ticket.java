package com.cinema.cinema;

import java.time.LocalDateTime;

    /**
     * Class main.cinema.Ticket defines a ticket that is associated with the showing of a movie
     * at a specific screen.
     *
     * @author Hari Rathod k22002783
     * @version 2022.12.10
     */
    public class Ticket
    {
        private int screenId;    // the screen id in which the movie is being shown
        private String movieTitle;
        private int rowNumber;
        private int seatNumber;   // column number = seat number
        private int cost;
        private LocalDateTime date;
        /**
         * Constructor for objects of class main.cinema.Ticket
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
         * Print the details of a ticket.
         */
        public String getDetails()
        {
            String details = "";
            details += "main.cinema.Screen ID: " + screenId + "\n"
                    + "Movie Title: " + movieTitle + "\n"
                    + "Row Number: " + rowNumber + "\n"
                    + "main.cinema.Seat Number: " + seatNumber + "\n"
                    + "Cost: " + cost + "\n"
                    + "Date: " + date;
            String dashes = "==============================";
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

