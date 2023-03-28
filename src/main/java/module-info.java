module com.cinema.cinema {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cinema.cinema to javafx.fxml;
    exports com.cinema.cinema;
}