module com.example.tema3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.tema3 to javafx.fxml;
    exports com.example.tema3;
    exports com.example.tema3;
    opens com.example.tema3 to javafx.fxml;
    exports com.example.tema3.actividad3_1;
    opens com.example.tema3.actividad3_1 to javafx.fxml;
}