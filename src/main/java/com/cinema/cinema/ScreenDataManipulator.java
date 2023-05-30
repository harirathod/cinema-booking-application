package com.cinema.cinema;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * This class interfaces with the MongoDB cloud database storing the screens, and provides methods for updating
 * screens in the database.
 * @author hari_rathod
 * @version 2023.05.30
 * @see Screen
 */
public class ScreenDataManipulator {
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
     */
    public Screen getScreenById(int id)
    {
        return null;
    }

    /**
     * Record the screen in the database. Overwrites the existing screen with matching id, if there is a matching id,
     * otherwise adds the provided screen as a 'new screen'.
     * @param screen The screen to be recorded.
     */
    public void recordScreen(Screen screen)
    {

    }

    /**
     * Update the movie screening in a screen.
     * @param id The id of the screen to be updated.
     * @param movieTitle The title of the movie.
     * @param ticketCost The cost of a ticket to watch this movie.
     */
    public void updateScreening(int id, String movieTitle, int ticketCost)
    {

    }

    /**
     * Remove the movie screening from a screen.
     * @param id The id of the screen to remove the movie screening from.
     */
    public void removeScreening(int id)
    {

    }

    /**
     * Delete a screen from the database.
     * @param id The id of the screen that is to be removed (deleted).
     */
    public void deleteScreen(int id)
    {

    }
}
