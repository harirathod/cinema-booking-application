package com.cinema.cinema;

import org.bson.BsonArray;
import org.bson.BsonBoolean;
import org.bson.BsonString;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class ScreenDocumentConverter {
    public static Screen convertDocumentToScreen(Document d)
    {
        ArrayList<ArrayList<Boolean>> seats = (ArrayList<ArrayList<Boolean>>) d.get("seats");

        // Create a screen with the appropriate id and seats.
        Screen screen = new Screen((Integer) d.get("_id"), seats.size(), seats.get(0).size());

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
                bsonRow.add(new BsonBoolean(true));
            }
            bsonSeats.add(bsonRow);
        }
        document.append("seats", bsonSeats);

        return document;
    }
}
