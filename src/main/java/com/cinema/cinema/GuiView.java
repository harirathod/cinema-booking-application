package com.cinema.cinema;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * A GUI view for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public class GuiView extends Application implements View {

    // To display messages.
    private Label output;

    // To receive user input.
    private TextField input;

    private ScrollPane scrollPane;

    /**
     * The method that starts the GUI application.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception If there was an error in starting the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        output = new Label();
        output.setAlignment(Pos.CENTER);
        input = new TextField();

        scrollPane = new ScrollPane(output);
        scrollPane.setPrefHeight(300);
        scrollPane.setPrefWidth(300);

        output.heightProperty().addListener((observable, oldValue, newValue) -> {
            output.layout();
            scrollPane.setVvalue(1.0d);
        });

        Pane root = new VBox(scrollPane, input);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Cinema Booking Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Display some text to the user, by appending this text to the end of the 'output label'.
     * @param text The text to be displayed.
     */
    @Override
    public void displayWithFormatting(String text) {
        display(getSeparator());
        display(text);
        display(getSeparator());
    }

    /**
     * Display an error dialog to the user.
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Display some text to the user, by appending this text to the end of the 'output label'.
     * @param text The text to be displayed.
     */
    @Override
    public void display(String text) {
        output.setText(output.getText() + "\n" + text);
    }
}
