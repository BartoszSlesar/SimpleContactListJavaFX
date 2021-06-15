package pl.bard.contact;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.bard.contact.datamodel.Contact;

import java.time.LocalDate;

public class DialogController {
//    TODO Add ability to modify Data

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextArea notesArea;


    public Contact addNewContact() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String notes = notesArea.getText().trim();

        Contact contact = new Contact(firstName, lastName, phoneNumber, notes);

        return contact;

    }

    public void populateContactDialog(Contact contact) {


            firstNameField.setText(contact.getFirstName());
            lastNameField.setText(contact.getLastName());
            phoneNumberField.setText(contact.getPhoneNumber());
            notesArea.setText(contact.getNotes());




    }

    public void updateContact(Contact contact) {

            contact.setFirstName(firstNameField.getText().trim());
            contact.setLastName(lastNameField.getText().trim());
            contact.setPhoneNumber(phoneNumberField.getText().trim());
            contact.setNotes(notesArea.getText().trim());

    }

}
