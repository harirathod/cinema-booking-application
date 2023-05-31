package com.cinema.cinema;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for CommandWordConvertor.
 * @author Hari Rathod
 * @version 2023.01.31
 */
public class CommandConverterTest {

    /**
     * Constructor for the test class.
     */
    public CommandConverterTest()
    {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    /**
     * Test the conversion of String to CommandWord happens correctly.
     */
    @Test
    public void testToCommandWord()
    {
        assertSame(CommandWord.BOOK, CommandConverter.convertToCommandWord("boOk", BookingType.CUSTOMER));
        assertSame(CommandWord.UNKNOWN, CommandConverter.convertToCommandWord("boOk", BookingType.MANAGER));
        assertSame(CommandWord.QUIT, CommandConverter.convertToCommandWord("finiSheD", BookingType.MANAGER));
        assertSame(CommandWord.UNKNOWN, CommandConverter.convertToCommandWord("null", BookingType.MANAGER));
        assertSame(CommandWord.UNKNOWN, CommandConverter.convertToCommandWord("0k", BookingType.CUSTOMER));
    }

    /**
     * Test the conversion of String to Command happens correctly.
     */
    @Test
    public void testToCommand()
    {
        assertSame(CommandWord.REMOVE, CommandConverter.convertToCommand("remove", BookingType.MANAGER).getCommandWord());
        assertSame(CommandWord.UNKNOWN, CommandConverter.convertToCommand("boOk", BookingType.MANAGER).getCommandWord());
        assertSame(CommandWord.QUIT, CommandConverter.convertToCommand("fInisHeD", BookingType.CUSTOMER).getCommandWord());
        assertSame(CommandWord.UNKNOWN, CommandConverter.convertToCommand("null", BookingType.MANAGER).getCommandWord());
        assertSame(CommandWord.UNKNOWN, CommandConverter.convertToCommand("0k", BookingType.CUSTOMER).getCommandWord());
    }
}
