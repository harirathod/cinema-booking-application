package com.cinema.cinema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

/**
     * Ticket defines a ticket that is associated with the showing of a movie
     * at a specific screen.
     *
     * @author Hari Rathod
     * @version 2023.03.28
     */
    public class Ticket implements Serializable
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
            String dashes = "===============================";

            String details = "";
            details += "Screen ID: " + screenId + "\n"
                    + "Movie Title: " + movieTitle + "\n"
                    + "Row Number: " + rowNumber + "\n"
                    + "Seat Number: " + seatNumber + "\n"
                    + "Cost: " + cost + "\n"
                    + "Date: " + getFormattedDate();

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

        private String getFormattedDate()
        {
            String date = this.date.toLocalDate() + " : ";
            LocalTime time = this.date.toLocalTime();
            date += time.minusNanos(time.getNano());
            return date;
        }

        /**
         * Convert a collection of tickets to a string representation.
         * @param tickets The collection of tickets.
         * @return The string representation of the tickets.
         */
        public static String getAllTicketsDetails(Collection<Ticket> tickets)
        {
            String details = "";
            for (Ticket ticket : tickets) {
                details += ticket.getDetails() + "\n";
            }
            return details;
        }
    }

