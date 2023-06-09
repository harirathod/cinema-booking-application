package com.cinema.cinema;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Filters.*;

/**
 * This class interfaces with the database storing the screens, and provides methods for accessing and modifying
 * screens in the database.
 * @author hari_rathod
 * @version 2023.05.30
 * @see Screen
 */
public class ScreenDataManipulator {

    // The uri which this class uses to connect to the database.
    private String uri;

    public ScreenDataManipulator()
    {
        this.uri = "mongodb+srv://" + System.getProperty("username") + ":" + System.getProperty("password") + "@cluster1.y3fcbqx.mongodb.net/?retryWrites=true&w=majority";
    }
    /**
     * Get all screens from the database.
     * @return A list of all the screens.
     */
    public List<Screen> getAllScreens()
    {
        List<Screen> screens = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Connect to the MongoDB database 'cinema', and get collection 'screens'.
            MongoCollection<Document> documentScreens = mongoClient.getDatabase("cinema").getCollection("screens");
            for (Document doc : documentScreens.find()) {
                screens.add(ScreenDocumentConverter.convertDocumentToScreen(doc));
            }
        }

        return screens;
    }

    /**
     * Get the screen with matching id.
     * @param id The id of the screen we want to retrieve.
     * @return The screen with the matching id.
     * @throws ScreenIdDoesNotExistException If the id was not matched with any of the screens in the cinema.
     */
    public Screen getScreenById(int id) throws ScreenIdDoesNotExistException
    {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> screens = mongoClient.getDatabase("cinema").getCollection("screens");
            Document doc = screens.find(eq("_id", id)).first();
            if (doc == null) {
                throw new ScreenIdDoesNotExistException("Screen with id %d does not exist".formatted(id));
            }
            return ScreenDocumentConverter.convertDocumentToScreen(doc);
        }
    }

    /**
     * Record the screen in the database. Overwrites the existing screen with matching id, if there is a matching id,
     * otherwise adds the provided screen as a 'new screen'.
     * @param screen The screen to be recorded.
     */
    public void recordScreen(Screen screen)
    {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> screens = mongoClient.getDatabase("cinema").getCollection("screens");
            // Although slightly longer, the below line with find(eq()).first() is more efficient than opening a new
            // connection with 'getScreenById()'.
            Document doc = screens.find(eq("_id", screen.getId())).first();
            if (doc == null) {
                screens.insertOne(ScreenDocumentConverter.convertScreenToDocument(screen));
            }
            else {
                screens.replaceOne(doc, ScreenDocumentConverter.convertScreenToDocument(screen));
            }
        }
    }

    /**
     * Update the movie screening in a screen. This does not clear the seats in the screen.
     * @param id The id of the screen to be updated.
     * @param movieTitle The title of the movie.
     * @param ticketCost The cost of a ticket to watch this movie.
     * @throws ScreenIdDoesNotExistException If the id was not matched with any of the screens in the cinema.
     */
    public void updateScreening(int id, String movieTitle, int ticketCost) throws ScreenIdDoesNotExistException
    {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> screens = mongoClient.getDatabase("cinema").getCollection("screens");
            /*
            Searching for the document here using find(eq(...)) is more efficient than making a separate method such as
            'getDocumentById(int id)' because the second method would open a second connection to the database, which
            would be less efficient than using the already open connection here.
             */
            Document doc = screens.find(eq("_id", id)).first();
            if (doc == null) {
                throw new ScreenIdDoesNotExistException("Screen with id %d does not exist".formatted(id));
            }

            Bson updates = combine(set("movieTitle", movieTitle), set("ticketCost", ticketCost), set("hasMovieScreening", true));
            screens.updateOne(doc, updates);
        }
    }

    /**
     * Remove the movie screening from a screen and empty the screen.
     * @param id The id of the screen to remove the movie screening from.
     */
    public void removeScreening(int id) throws ScreenIdDoesNotExistException
    {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> screens = mongoClient.getDatabase("cinema").getCollection("screens");
            Document doc = screens.find(eq("_id", id)).first();
            if (doc == null) {
                throw new ScreenIdDoesNotExistException("Screen with id %d does not exist".formatted(id));
            }
            Bson seatsUpdate = set("seats.$[].$[]", true);
            Bson updates = combine(set("movieTitle", null), set("ticketCost", 0), set("hasMovieScreening", false), seatsUpdate);
            screens.updateOne(doc, updates);
        }
    }

    /**
     * Delete a screen from the database.
     * @param id The id of the screen that is to be removed (deleted).
     */
    public void deleteScreen(int id) throws ScreenIdDoesNotExistException {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> screens = mongoClient.getDatabase("cinema").getCollection("screens");
            Document doc = screens.find(eq("_id", id)).first();
            if (doc == null) {
                throw new ScreenIdDoesNotExistException("Screen with id %d does not exist".formatted(id));
            }
            screens.deleteOne(doc);
        }
    }
}
