module com.cinema.cinema {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;


    opens com.cinema.cinema to javafx.fxml;
    exports com.cinema.cinema;
}