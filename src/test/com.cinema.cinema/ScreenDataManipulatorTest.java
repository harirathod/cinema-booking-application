package com.cinema.cinema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ScreenDataManipulator. As ScreenDataManipulator uses a database, which is a form of persistent storage,
 * the tests may not work after the user has heavily interacted with the database.
 *
 */
class ScreenDataManipulatorTest {
    public ScreenDataManipulator screenDataManipulator;

    @BeforeEach
    public void setUp()
    {
        screenDataManipulator = new ScreenDataManipulator();
    }

    @Test
    void testRecordScreen()
    {
        assertEquals(1, screenDataManipulator.getAllScreens().size());
        Screen screen0 = new Screen(0, 20, 32);
        screenDataManipulator.recordScreen(screen0);
        assertEquals(1, screenDataManipulator.getAllScreens().size());
        Screen screen1 = new Screen(1, 12, 20);
        screenDataManipulator.recordScreen(screen1);
        assertEquals(2, screenDataManipulator.getAllScreens().size());

        screenDataManipulator.recordScreen(screen0);
        screenDataManipulator.recordScreen(screen1);

        try {
            Screen screen = screenDataManipulator.getScreenById(0);
            assertEquals(640, screen.getNumberOfSeats());
        } catch (ScreenIdDoesNotExistException e) {
            fail();
        }

        try {
            // This is to ensure the adding of screen with id 1 will not affect other tests that use the same database.
            screenDataManipulator.deleteScreen(1);
        } catch (ScreenIdDoesNotExistException e) {
            fail();
        }
    }

    @Test
    void testGetAllScreens()
    {
        List<Screen> l = screenDataManipulator.getAllScreens();
        assertEquals(1, l.size());
    }

    @Test
    void testGetScreenById()
    {
        Screen screen;
        try {
            screen = screenDataManipulator.getScreenById(0);
            assertEquals(0, screen.getId());
        } catch (ScreenIdDoesNotExistException e) {
            fail();
        }

        try {
            screen = screenDataManipulator.getScreenById(1);
            fail();
        } catch (ScreenIdDoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    void testUpdateAndRemoveScreening()
    {
        Screen screen;
        try {
            screen = screenDataManipulator.getScreenById(0);
            assertFalse(screen.hasMovieScreening());

            screenDataManipulator.updateScreening(0, "Movie", 1650);
            screen = screenDataManipulator.getScreenById(0);
            assertTrue(screen.hasMovieScreening());

            screenDataManipulator.removeScreening(0);
            screen = screenDataManipulator.getScreenById(0);
            assertFalse(screen.hasMovieScreening());
        } catch (ScreenIdDoesNotExistException e) {
            fail();
        }
    }

    @Test
    void testDeleteScreen()
    {
        assertEquals(1, screenDataManipulator.getAllScreens().size());
        try {
            screenDataManipulator.deleteScreen(1);
            fail();
        } catch (ScreenIdDoesNotExistException e) {
            assertEquals(1, screenDataManipulator.getAllScreens().size());
        }
        Screen screen1 = new Screen(1, 12, 20);
        screenDataManipulator.recordScreen(screen1);
        assertEquals(2, screenDataManipulator.getAllScreens().size());
        try {
            screenDataManipulator.deleteScreen(1);
            assertEquals(1, screenDataManipulator.getAllScreens().size());
        } catch (ScreenIdDoesNotExistException e) {
            fail();
        }
    }
}