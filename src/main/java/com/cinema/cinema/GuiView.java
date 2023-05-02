package com.cinema.cinema;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A JavaFX GUI view, for the cinema booking application.
 * @author hari_rathod
 * @version 2023.04.12
 */
public class GuiView extends Application implements View {

    // A reference to the current GuiView object. Allows the GuiView object to be accessed, even after being launched.
    private static GuiView thisGuiView;

    // This latch lets other threads know when the instance of this class has finished construction.
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    // To display messages.
    private Label output;

    // To receive user input.
    private TextField input;
    private Button submitButton;

    // Used for the management of user input across multiple threads.
    private BlockingQueue<String> blockingQueue;
    private Stage stage;

    /**
     * Constructor.
     */
    public GuiView() {
        thisGuiView = this;
    }

    /**
     * Launches this JavaFX class.
     */
    @Override
    public void start() {
        try {
            start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the input from the user.
     *
     * @return The user input.
     */
    public String getInput() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        this.stage = primaryStage;
        blockingQueue = new LinkedBlockingQueue<>(1);

        // Set up output field.
        output = new Label();
        VBox outputPane = new VBox(output);
        outputPane.setAlignment(Pos.TOP_CENTER);

        // Set up input field.
        input = new TextField();
        submitButton = new Button("Enter text");
        submitButton.setOnAction(e -> submitText());

        // Place the text display panel in a scroll pane, and set the scroll bar to be scrolled to the bottom,
        // for the user's convenience.
        ScrollPane scrollPane = new ScrollPane(outputPane);
        scrollPane.setPrefHeight(300);
        scrollPane.setPrefWidth(400);
        setScrollPaneVBarToBottom(scrollPane);

        Pane root = new VBox(scrollPane, new HBox(input, submitButton));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");

        // If the user presses 'ENTER', this submits the text in the input field.
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), () -> submitButton.fire());

        primaryStage.setTitle("Cinema Booking Application");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        // Let other threads know that this class has finished construction.
        countDownLatch.countDown();
    }

    /**
     * Open the save dialogue-window and prompt the user for a location to save their tickets to.
     * @return The file that the user selected to save to.
     */
    public File getSelectedSaveFile()
    {
        FileChooser fileChooser = new FileChooser();
        // Save the file to the user's current directory by default.
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        return fileChooser.showSaveDialog(stage);
    }

    /**
     * Set this ScrollPane to be scrolled to the bottom, even when its content changes.
     * @param scrollPane The scrollPane that we want to be scrolled to the bottom.
     */
    private void setScrollPaneVBarToBottom(ScrollPane scrollPane)
    {
        output.heightProperty().addListener((observable, oldValue, newValue) -> {
            output.layout();
            scrollPane.setVvalue(1.0d);
        });
    }

    /**
     * Take text from the 'input' label, and display it in the 'output' label.
     */
    private void submitText()
    {
        String text = input.getText();
        input.clear();
        // Uncomment this below line, to show the user what they've typed in.
        // display(text);

        if(!blockingQueue.offer(text)) displayError("There was an error reading your input",
                "Apologies, we could not process your user input at this time.");
    }


    /**
     * Display formatted text to the user, in the 'output label'.
     * @param text The text to be displayed.
     */
    @Override
    public void displayWithFormatting(String text)
    {
        display(text);
        display(getSeparator());
    }

    /**
     * Display an error dialog to the user, with no header text but only content text.
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Display an error dialog to the user, with both header and content text.
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
     * @param text The text to be displayed.
     */
    @Override
    public void display(String text) {
        output.setText(output.getText() + "\n" + text);
    }

    /**
     * Get the instance of the GuiView currently active.
     * @return The instance of the GuiView class that is currently running.
     */
    public static GuiView getInstance()
    {
        try {
            // Blocks until the instance is correctly constructed.
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return thisGuiView;
    }
}

