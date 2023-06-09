package com.cinema.cinema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandWordTest {

    @Test
    void testToString() {
    }

    @Test
    void getPlaceholder() {
    }

    /**
     * Test that all commands' placeholders work correctly.
     */
    @Test
    public void testPlaceholder()
    {

        assertFalse(CommandWord.QUIT.hasPlaceholder());
        assertTrue(CommandWord.ADD.hasPlaceholder());

        assertNull(CommandWord.QUIT.getPlaceholder());
        assertEquals("movie", CommandWord.ADD.getPlaceholder());
    }

    /**
     * Test that all commands' CommandString are provided correctly..
     */
    @Test
    public void testCommandString()
    {
        assertEquals("remove", CommandWord.REMOVE.getCommandString());
        assertEquals("book", CommandWord.BOOK.getCommandString());
        assertNull(CommandWord.UNKNOWN.getCommandString());
    }
}