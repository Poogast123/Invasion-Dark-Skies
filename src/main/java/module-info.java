module com.example.spacegame {
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
    requires javafx.media;
    requires jdk.jfr;
    requires javafx.graphics;
    requires java.desktop;

    opens com.example.spacegame to javafx.fxml;
    exports com.example.spacegame;
    exports Game;
    opens Game to javafx.fxml;
}