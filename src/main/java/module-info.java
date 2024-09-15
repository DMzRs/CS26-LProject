module icedcoffee.coldbrewco {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;

    opens icedcoffee.coldbrewco to javafx.fxml;
    exports icedcoffee.coldbrewco;
}