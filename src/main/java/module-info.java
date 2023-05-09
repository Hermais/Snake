module com.example.snakeattempt {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.snakeattempt to javafx.fxml;
    exports com.example.snakeattempt;
}