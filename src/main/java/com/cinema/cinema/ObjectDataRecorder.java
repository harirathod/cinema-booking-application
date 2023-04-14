package com.cinema.cinema;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * This class writes serializable objects to a file. Multiple objects can be written to the file.
 * This means that all objects are stored in List,
 * and this List is then written to the file.
 * This allows objects to be stored persistently.
 *
 * @author hari_rathod
 * @version 2023.04.03
 */
public class ObjectDataRecorder<E> {
    private final Filename FILENAME;
    private final Class<E> type;

    /**
     * Constructor for ObjectDataRecorder.
     *
     * @param filename The name of the file to write objects to.
     */
    public ObjectDataRecorder(Filename filename, Class<E> type) {
        this.FILENAME = filename;
        this.type = type;
    }

    /**
     * Write (append) an object to the file.
     *
     * @param object The object to be written.
     * @throws IOException            If there was an error reading from or writing to the file.
     * @throws ClassNotFoundException If the file written to already has objects, of a different type,
     *                                to the object provided here.
     */
    public void writeToFile(E object) throws IOException, ClassNotFoundException {
        // Get the existing list of objects in the file and add this object to the list.
        List<E> objects = readListOfObjectsFromFile();
        objects.add(object);

        // Write the list of objects back to the file.
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(Path.of(FILENAME.toString())))) {
            outputStream.writeObject(objects);
        }
    }

    /**
     * Get the list of objects currently stored in the file.
     *
     * @return The list of objects currently being stored in the file.
     * @throws IOException            If there was an error reading from the file.
     * @throws ClassNotFoundException If the objects read were not of the same type as this ObjectDataRecorder's generic.
     */
    public List<E> readListOfObjectsFromFile() throws IOException, ClassNotFoundException {
        Path path = Path.of(FILENAME.toString());
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
     * Validates that the objects in the list read are of the same type as this ObjectDataRecorder's type.
     *
     * @param objects The objects to be validated.
     * @throws ClassNotFoundException If the objects in the file are not of the same type as the ObjectDataRecorder's type.
     */
    private void validateTypeOfList(List<?> objects) throws ClassNotFoundException {
        for (Object object : objects) {
            if (object != null && !(type.isInstance(object))) {
                throw new ClassNotFoundException("Error: Objects of type %s not found in file: %s".formatted(this.type, FILENAME));
            }
        }
    }

    /**
     * Get number of objects that are stored in the file.
     *
     * @throws IOException            If there was an error handling the file.
     * @throws ClassNotFoundException If the objects in the file are not of the same type as the ObjectDataRecorder's type.
     */
    public int getNumberOfObjects() throws IOException, ClassNotFoundException {
        List<E> list = readListOfObjectsFromFile();
        return list.size();
    }


    /**
     * Creates a new file if it doesn't exist, otherwise, this method clears the existing file.
     *
     * @throws IOException If there was an error handling the file.
     */
    public void resetFile() throws IOException {
        Path path = Path.of(FILENAME.toString());
        if (Files.exists(path)) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(
                    Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING))) {
                // Nothing to do here.
            }
        } else {
            Files.createFile(path);
            resetFile();
        }
    }

    /**
     * Get the filename that this class is writing to.
     *
     * @return The filename being written to.
     */
    public Filename getFILENAME() {
        return FILENAME;
    }
}