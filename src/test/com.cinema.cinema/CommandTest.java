package com.cinema.cinema;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


/**
 * The test class for main.cinema.Command.
 * @author Hari Rathod
 * @version 2023.01.31
 */
public class CommandTest {
    public CommandTest()
    {
    }

    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    /**
     * Test that a main.cinema.Command is created correctly.
     */
    @Test
    public void testCommand()
    {
        Command command = new Command(CustomerCommandWord.BOOK, "Avatar 3 - 2023");
        assertEquals(CustomerCommandWord.BOOK, command.getCommandWord());
        assertEquals("Avatar 3 - 2023", command.getSecondWord());
    }
}
