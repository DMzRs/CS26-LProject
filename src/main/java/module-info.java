module icedcoffee.coldbrewco {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;

    // Open the icedcoffee.coldbrewco package to javafx.fxml
    opens icedcoffee.coldbrewco to javafx.fxml;

    // Open the Database package to javafx.base for reflection access
    opens Database to javafx.base;

    exports icedcoffee.coldbrewco;
}