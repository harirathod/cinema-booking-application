package com.cinema.cinema;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A GUI view for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public class GuiView extends Application implements View {

    // A reference to the current GuiView object. Allows the GuiView object to be accessed, even after being launched.
    private static GuiView thisGuiView;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    // To display messages.
    private Label output;

    // To receive user input.
    private TextField input;
    private Button submitButton;
    //private BlockingQueue<String> blockingQueue;

    /**
     * Constructor.
     */
    public GuiView() {
        thisGuiView = this;
        countDownLatch.countDown();
    }

    /**
     * Launches this JavaFX class.
     */
    @Override
    public void start() {
        launch("");
    }

    /**
     * Get the input from the user.
     *
     * @return The user input.
     */
    public String getInput() {
        return "testing";
    }

    /**
     * The method that starts the GUI application.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception If there was an error in starting the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //blockingQueue = new LinkedBlockingQueue<>();

        output = new Label();

        output.setAlignment(Pos.CENTER);
        input = new TextField();
        submitButton = new Button("Enter text");
        submitButton.setOnAction(e -> {
            String text = input.getText();
            input.clear();
            display(text);
            //blockingQueue.offer(text);
            //submitButton.setDisable(true);
            //if(!blockingQueue.offer(text)) displayError("There was an error reading your input"
            //        , "Apologies, we could not process your user input at this time.");
        });

        ScrollPane scrollPane = new ScrollPane(output);
        scrollPane.setPrefHeight(300);
        scrollPane.setPrefWidth(300);

        output.heightProperty().addListener((observable, oldValue, newValue) -> {
            output.layout();
            scrollPane.setVvalue(1.0d);
        });

        Pane root = new VBox(scrollPane, new HBox(input, submitButton));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Cinema Booking Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Display some text to the user, by appending this text to the end of the 'output label'.
     *
     * @param text The text to be displayed.
     */
    @Override
    public void displayWithFormatting(String text) {
        display(getSeparator());
        display(text);
        display(getSeparator());
    }

    /**
     * Display an error dialog to the user, with no header text but only content text.
     *
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Display an error dialog to the user, with both header and content text.
     *
     * @param title   The title of the error message.
     * @param message The content of the error message.
     */
    @Override
    public void displayError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Display some text to the user, by appending this text to the end of the 'output label'.
     *
     * @param text The text to be displayed.
     */
    @Override
    public void display(String text) {
        output.setText(output.getText() + "\n" + text);
    }

    /**
     * Get the instance of the GuiView currently running.
     * @return The instance of the GuiView class that is running.
     */
    public static GuiView getInstance()
    {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return thisGuiView;
    }
}

