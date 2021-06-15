module JavaFXContactList {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml;
    requires javafx.base;
    requires javafx.graphics;

    opens pl.bard.contact.datamodel;
    opens pl.bard.contact;
}