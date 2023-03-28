package com.cinema.cinema;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for CommandWordConvertor.
 * @author Hari Rathod
 * @version 2023.01.31
 */
public class CommandWordConverterTest {

    /**
     * Constructor for the test class.
     */
    public CommandWordConverterTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    /**
     * Test the conversion of String to main.cinema.CommandWord happens correctly.
     */
    @Test
    public void testToCommandWord()
    {
        assertSame(CommandWord.HELP, CommandWordConverter.toCommandWord("hElp"));
        assertSame(CommandWord.BOOK, CommandWordConverter.toCommandWord("boOk"));
        assertSame(CommandWord.QUIT, CommandWordConverter.toCommandWord("finiSheD"));
        assertSame(CommandWord.UNKNOWN, CommandWordConverter.toCommandWord("null"));
        assertSame(CommandWord.UNKNOWN, CommandWordConverter.toCommandWord("0k"));
    }
}
