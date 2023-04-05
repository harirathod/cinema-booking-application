package com.cinema.cinema;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;

 /**
 * This class writes serializable objects to a file. Multiple objects can be written to the file.
 * This means that all objects are stored in List,
 * and this List is then written to the file.
 * This allows objects to be stored persistently.
 * @author hari_rathod
 * @version 2023.04.03
 */
public class ObjectRecorder<E> {
    private final String FILENAME;
    public final Class<E> type;

    /**
     * Constructor for ObjectRecorder.
     * @param filename The name of the file to write objects to.
     */
    public ObjectRecorder(String filename, Class<E> type) {
        this.FILENAME = filename;
        this.type = type;
    }

    /**
     * Write (append) an object to the file.
     * @param object The object to be written.
     * @throws IOException If there was an error reading from or writing to the file.
     * @throws ClassNotFoundException If the file written to already has objects, of a different type,
     * to the object provided here.
     */
    public void writeToFile(E object) throws IOException, ClassNotFoundException
    {
        // Get the existing list of objects in the file and add this object to the list.
        List<E> objects = readListOfObjectsFromFile();
        objects.add(object);

        // Write the list of objects back to the file.
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(Path.of(FILENAME)))) {
            outputStream.writeObject(objects);
        }
    }

    /**
     * Get the list of objects currently stored in the file.
     * @return The list of objects currently being stored in the file.
     * @throws IOException If there was an error reading from the file.
     * @throws ClassNotFoundException If the objects read were not of the same type as this ObjectRecorder's generic.
     */
    private List<E> readListOfObjectsFromFile() throws IOException, ClassNotFoundException
    {
        Path path = Path.of(FILENAME);
        try (ObjectInputStream inputStream = new ObjectInputStream(
                Files.newInputStream(path))) {
            try {
                List<E> objects = (List<E>) inputStream.readObject();
                // Validate that all objects in
                validateTypeOfList(objects);
                return objects;
            }
            // If there are no objects in the file, create a new list and return it.
            catch (EOFException e) {
                return new ArrayList<>();
            }
        }
    }

    /**
     * Validates that the objects in the list read are of the same type as this ObjectRecorder's type.
     * @param objects The objects to be validated.
     * @throws ClassNotFoundException If the objects in the file are not of the same type as the ObjectRecorder's type.
     */
    private void validateTypeOfList(List<?> objects) throws ClassNotFoundException {
        for (Object object : objects) {
            if (object != null && !(type.isInstance(object))) {
                throw new ClassNotFoundException("Error: Objects of type %s not found in file: %s".formatted(this.type, FILENAME));
            }
        }
    }


    /**
     * Creates a new file if it doesn't exist, otherwise, this method clears the existing file.
     * @throws IOException If there was an error handling the file.
     */
    private void resetFile() throws IOException
    {
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