package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import kaymattern.notus.model.Subject;
import kaymattern.notus.validation.ValidationType;
import kaymattern.notus.validation.Validator;

public class EditSubject extends AbstractDialogController {

    @FXML private TextField nameTextField;

    private Subject subject;

    /**
     * Sets the current subject
     * @param subject the subject to edit
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @FXML
    private void delete() {
        boolean delete = getApp().showAlert(Alert.AlertType.CONFIRMATION,
                "Loeschen?",
                "Willst du wirklich das Fach " + this.subject.getName() + " loeschen?",
                "Alle Noten dieses Faches werden ebenfalls geloescht.");

        if (delete) {
            getApp().getDataAccessor().removeSubject(this.subject);
            close();
        }
    }

    @FXML
    private void edit() {
        if (! this.subject.getName().equals(this.nameTextField.getText())) {
            if (! validateUi()) {
                return;
            }
            getApp().getDataAccessor().editSubject(this.subject, this.nameTextField.getText());
        }
        close();
    }

    @Override
    protected void initValidator(Validator validator) {
        validator.register(ValidationType.TEXT, this.nameTextField.textProperty(), "Name");
    }

    @Override
    public void entered() {
        this.nameTextField.setText(this.subject.getName());
    }

}
