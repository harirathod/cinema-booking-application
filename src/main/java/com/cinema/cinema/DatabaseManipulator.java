package com.cinema.cinema;

import java.io.IOException;

/**
 * This class is used to ensure the ticket and screen files are set up appropriately.
 */
public class DatabaseManipulator {
    private final ObjectDataRecorder<Ticket> ticketDataRecorder = new ObjectDataRecorder<>(Filename.TICKET, Ticket.class);
    private final ObjectDataRecorder<Screen> screenDataRecorder = new ObjectDataRecorder<>(Filename.SCREEN, Screen.class);

    /**
     * Initialise fields. Ensure the ticket and screen files are reset.
     * @throws IOException If there was an error handling the files.
     */
    public DatabaseManipulator() throws IOException {
        ticketDataRecorder.resetFile();
        screenDataRecorder.resetFile();
    }

    public void addScreen(Screen screen) throws IOException, ClassNotFoundException {
        screenDataRecorder.writeToFile(screen);
    }
}
