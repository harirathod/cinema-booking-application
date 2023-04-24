package com.cinema.cinema;

public interface CommandWord
{
    String getCommandString();
    String getPlaceholder();
    boolean hasPlaceholder();


    <E extends CommandWord> E convertToCommandWord(String commandString);

    <E extends CommandWord> E getQuitCommand();
}
