package com.cinema.cinema;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class displays a graphical UI to the user, and lets them select whether they want to use the CustomerBooking,
 * or the ManagerBooking.
 * @author hari_rathod
 * @version 2023.04.25
 */
public class BookingChooser extends Application {

    // CountDownLatch to track when this BookingChooser finishes loading? TODO ######################
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    // Singleton pattern to track global instance of BookingChooser.
    private static BookingChooser bookingChooser;

    // Variable used to provide information about the BookingType selected by the user, across multiple threads.
    private BlockingQueue<BookingType> blockingQueue;
    private Stage stage;
    private ToggleGroup toggleGroup;

    /**
     * Constructor.
     */
    public BookingChooser() {
        bookingChooser = this;
    }

    /**
     * Starts the BookingChooser.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        BorderPane root = new BorderPane();
        toggleGroup = new ToggleGroup();
        RadioButton customer = new RadioButton(BookingType.CUSTOMER.toString());
        customer.setToggleGroup(toggleGroup);
        RadioButton manager = new RadioButton(BookingType.MANAGER.toString());
        manager.setToggleGroup(toggleGroup);

        blockingQueue = new LinkedBlockingQueue<>();
        Button button = new Button("Submit");
        button.setDisable(true);
        button.setOnMousePressed(this::moveNode);
        button.setOnMouseReleased(this::moveNodeBack);
        button.setOnAction(this::submitSelection);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            button.setDisable(false);
            button.setEffect(new DropShadow(0, 4, 4, Color.BLACK));
        });

        VBox selectionBox = new VBox(customer, manager);
        selectionBox.setId("selection-box");
        VBox selectionBoxWithButton = new VBox(selectionBox, button);
        selectionBoxWithButton.setId("box-with-button");
        root.setCenter(selectionBoxWithButton);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("style2.css");
        stage.setScene(scene);
        stage.setTitle("Cinema Booking Application");
        stage.show();
        countDownLatch.countDown();
    }

    /**
     * Once the user has made their selection and presses the submit button, the corresponding BookingType is
     * added to the BlockingQueue field that is maintained by this class.
     * @param actionEvent The associated event.
     */
    private void submitSelection(ActionEvent actionEvent)
    {
        String selection = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
        for (BookingType b : BookingType.values()) {
            if (b.toString().equals(selection)) {
                blockingQueue.offer(b);
            }
        }
        stage.close();
    }

    /**
     * Move the node to make it look as if it has been pressed down physically.
     * @param mouseEvent The associated event.
     */
    private void moveNode(MouseEvent mouseEvent)
    {
        Node node = (Node) mouseEvent.getSource();
        TranslateTransition t = new TranslateTransition(Duration.millis(1), node);
        t.setToX(4);
        t.setToY(4);
        t.play();
        node.setEffect(new DropShadow(0, 1, 1, Color.BLACK));
    }

    /**
     * Move the node back to its original position.
     * @param mouseEvent The associated event.
     */
    private void moveNodeBack(MouseEvent mouseEvent)
    {
        Node node = (Node) mouseEvent.getSource();
        TranslateTransition t = new TranslateTransition(Duration.millis(1), node);
        t.setToX(0);
        t.setToY(0);
        t.play();
        node.setEffect(new DropShadow(0, 4, 4, Color.BLACK));
    }

    /**
     * Gets the user's selection. Blocks the thread until the user has submitted their selection.
     * @return The user's selected booking type.
     */
    public BookingType getSelectedBookingType()
    {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the BookingChooser instance that is currently active. This method blocks until the BookingChooser
     * instance has finished construction.
     * @return The instance of this class that is currently active.
     */
    public static BookingChooser getBookingChooser()
    {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return bookingChooser;
    }
}
