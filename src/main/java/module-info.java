module com.example.snakeattempt {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires javafx.graphics;
  // requires commons.cli;
    //requires com.zaxxer.hikari;
    requires java.sql;
    opens com.example.snakeattempt to javafx.fxml;
    exports com.example.snakeattempt;
}