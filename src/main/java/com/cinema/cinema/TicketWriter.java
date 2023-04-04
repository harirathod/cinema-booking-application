package com.cinema.cinema;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class writes serializable Ticket objects to a file. This allows Ticket objects to be stored persistently.
 * @author hari_rathod
 * @version 2023.04.03
 * @see Ticket
 */
public class TicketWriter {
    private final String FILENAME;

    /**
     * Constructor for TicketWriter.
     * @param filename The name of the file to write objects to.
     */
    public TicketWriter(String filename) throws IOException {
        this.FILENAME = filename;
    }

    /**
     * Write (append) a ticket to the file.
     * @param ticket The ticket to be written.
     * @throws IOException If there was an error reading from or writing to the file.
     */
    public void writeToFile(Ticket ticket) throws IOException {
        // Get the existing list of tickets in the file and add this ticket to the list.
        List<Ticket> tickets;
        try {
            tickets = readListOfTickets();
        }
        catch (TicketsNotFoundException e) {
            // If there is not existing list of tickets, create a new list of tickets.
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);

        // Write the list of tickets back to the file.
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(Path.of(FILENAME)))) {
            outputStream.writeObject(tickets);
        }
    }

    /**
     * Get the list of tickets currently stored in the file.
     * @return The list of tickets currently being stored in the file.
     * @throws IOException If there was an error reading from the file.
     * @throws TicketsNotFoundException If no tickets were found in the file.
     */
    private List<Ticket> readListOfTickets() throws IOException, TicketsNotFoundException
    {
        Path path = Path.of(FILENAME);
        if (Files.exists(path)) {
            try (ObjectInputStream inputStream = new ObjectInputStream(
                    Files.newInputStream(path))) {
                try {
                    return (List<Ticket>) inputStream.readObject();
                } catch (EOFException e) {
                    return new ArrayList<>();
                }
            } catch (ClassCastException | ClassNotFoundException e) {
                throw new TicketsNotFoundException("Tickets not found in file: " + e.getMessage());
            }
        } else {
            Files.createFile(path);
            return new ArrayList<>();
        }
    }

    /**
     * Clears the file of all content.
     * @throws IOException If there was an error handling the file.
     */
    private void removeAllTickets() throws IOException {
        Path path = Path.of(FILENAME);
        if (Files.exists(path)) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(
                    Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING))) {
                // Nothing to do here.
            }
        } else {
            Files.createFile(path);
        }
    }
}