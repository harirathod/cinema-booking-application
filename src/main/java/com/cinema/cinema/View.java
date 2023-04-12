package com.cinema.cinema;

public interface View {
    void displayWithFormatting(String message);
    void displayError(String message);
    void display(String message);
}
