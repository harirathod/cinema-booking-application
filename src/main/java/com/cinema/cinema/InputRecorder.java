package com.cinema.cinema;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * This class records the history of user input in a file.
 */
public class InputRecorder {
    private final String FILENAME;

    public InputRecorder(String FILENAME) throws IOException
    {
        this.FILENAME = FILENAME;
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILENAME))) {
            // File is opened in write mode, automatically clearing it.
        }
    }
    /**
     * Write a string to the file storing user input history.
     * @param string The string to write to the history.
     * @throws IOException If there was an error writing to the file.
     */
    public void writeStringToFile(String string) throws IOException {
        Path path = Path.of(FILENAME);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(string);
            writer.newLine();
        }
    }
}
