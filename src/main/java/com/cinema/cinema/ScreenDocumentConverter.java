package com.cinema.cinema;

import org.bson.BsonArray;
import org.bson.BsonBoolean;
import org.bson.BsonString;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class ScreenDocumentConverter
{
    public static Screen convertDocumentToScreen(Document d)
    {
        ArrayList<ArrayList<Boolean>> docSeats = (ArrayList<ArrayList<Boolean>>) d.get("seats");
        boolean[][] seats = new boolean[docSeats.size()][docSeats.get(0).size()];
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                seats[i][j] = docSeats.get(i).get(j);
            }
        }

        // Create a screen with the appropriate id and seats.
        Screen screen = new Screen((Integer) d.get("_id"), docSeats.size(), docSeats.get(0).size());
        screen.setSeats(seats);

        // If a screen has a screening, set its movie title and ticket cost.
        if ((Boolean) d.get("hasMovieScreening")) {
            screen.addNewMovie((String) d.get("movieTitle"), (Integer) d.get("ticketCost"));
        } else {
            screen.removeMovie();
        }

        return screen;
    }

    public static Document convertScreenToDocument(Screen screen)
    {
        Document document = new Document("_id", screen.getId());
        document.append("hasMovieScreening", screen.hasMovieScreening())
                .append("movieTitle", screen.getMovieTitle())
                .append("ticketCost", screen.getTicketCost());
        // Convert the List of seats to a BSON array.
        BsonArray bsonSeats = new BsonArray();
        for (boolean[] row : screen.getSeats()) {
            BsonArray bsonRow = new BsonArray();
            for (boolean seat : row) {
                bsonRow.add(new BsonBoolean(seat));
            }
            bsonSeats.add(bsonRow);
        }
        document.append("seats", bsonSeats);

        return document;
    }
}
