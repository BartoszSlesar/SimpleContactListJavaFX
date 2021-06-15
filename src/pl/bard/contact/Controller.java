package pl.bard.contact;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import pl.bard.contact.datamodel.Contact;
import pl.bard.contact.datamodel.ContactData;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> tableView;

    @FXML
    private BorderPane mainBorderPane;

    private ContactData contactData;


    public void initialize() {
        contactData = new ContactData();
        contactData.loadContacts();
        tableView.setItems(contactData.getContacts());


    }

    @FXML
    public void showNewContactDialog() {

        FXMLLoader fXMLLoaderContact = new FXMLLoader();
        fXMLLoaderContact.setLocation(getClass().getResource("/contactDialog.fxml"));
        Dialog<ButtonType> dialog = getContactDialog("Add new Contact", fXMLLoaderContact);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fXMLLoaderContact.getController();
            Contact contact = controller.addNewContact();
            contactData.addContact(contact);
            saveContacts();
        }

    }

    @FXML
    public void showEditContactDialog() {

        Contact contact = tableView.getSelectionModel().getSelectedItem();
        if (contact != null) {
            FXMLLoader fXMLLoaderContact = new FXMLLoader();
            fXMLLoaderContact.setLocation(getClass().getResource("/contactDialog.fxml"));
            Dialog<ButtonType> dialog = getContactDialog("Edit Contact", fXMLLoaderContact);

            DialogController controller = fXMLLoaderContact.getController();

            controller.populateContactDialog(contact);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                controller.updateContact(contact);
                saveContacts();
            }
        }


    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

    @FXML
    public void handleDeletionEvent() {
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        if (contact != null) {
            deleteItem(contact);
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        if (contact != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(contact);
            }
        }
    }

    private void deleteItem(Contact contact) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete item: " + contact.getFirstName() + " " + contact.getLastName());
        alert.setContentText("Are you sure? Press OK to confirm or cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            contactData.deleteContact(contact);
            saveContacts();
        }
    }

    private void saveContacts() {
        Runnable save = () -> this.contactData.saveContacts();
        save.run();

    }

    private Dialog<ButtonType> getContactDialog(String title, FXMLLoader fxmlLoader) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle(title);

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return new Dialog<>();
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        return dialog;
    }


}
