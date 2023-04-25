package com.cinema.cinema;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class displays a graphical UI to the user, and lets them select whether they want to use the CustomerBooking,
 * or the ManagerBooking.
 * @author hari_rathod
 * @version 2023.04.25
 */
public class BookingChooser extends Application {
    private static BookingChooser bookingChooser;
    /**
     * Starts the BookingChooser.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        ToggleGroup group = new ToggleGroup();
        RadioButton customer = new RadioButton("Customer");
        customer.setToggleGroup(group);
        RadioButton manager = new RadioButton("Manager");
        manager.setToggleGroup(group);

        Button button = new Button("Submit");
        button.setDisable(true);
        button.setOnMousePressed(this::moveNode);
        button.setOnMouseReleased(this::moveNodeBack);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            button.setDisable(false);
            button.setEffect(new DropShadow(0, 4, 4, Color.BLACK));
        });

        VBox selectionBox = new VBox(customer, manager);
        selectionBox.setId("selection-box");
        VBox selectionBoxWithButton = new VBox(selectionBox, button);
        selectionBoxWithButton.setId("box-with-button");
        root.setCenter(selectionBoxWithButton);

        //root.setAlignment(Pos.CENTER_LEFT);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style2.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cinema Booking Application");
        primaryStage.show();
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
}
