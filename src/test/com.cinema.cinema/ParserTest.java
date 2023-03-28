package com.cinema.cinema;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * Test class for main.cinema.Parser.
 * @author Hari Rathod
 * @version 2023.02.05
 */
public class ParserTest {
    Parser parser = new Parser();

    public ParserTest()
    {
        // nothing to do here
    }

    @BeforeEach
    public void setUp()
    {
        // nothing to do here
    }

    @AfterEach
    public void tearDown()
    {
        // nothing to do here
    }

    /**
     * Test that all commands are printed out correctly.
     */
    @Test
    public void testGetAllCommands()
    {
        assertEquals("Commands available:\nhelp\nfinished\nbook <movie title>\nlist <movies>\nbasket", parser.getAllCommands());
    }
}
