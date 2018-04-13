package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import kaymattern.notus.validation.ValidationType;
import kaymattern.notus.validation.Validator;

public class AddSubject extends AbstractDialogController {

    @FXML private TextField nameTextField;

    @FXML
    private void add() {
        if (! validateUi()) {
            return;
        }

        String name = this.nameTextField.getText();
        getApp().getDataAccessor().createSubject(name);
        close();
        getApp().showAlert(Alert.AlertType.INFORMATION, "Status", "Fach " + name + " wurde hinzugefuegt", null);
    }

    @Override
    protected void initValidator(Validator validator) {
        validator.register(ValidationType.TEXT, this.nameTextField.textProperty(), "Name");
    }

    @Override
    public void entered() {
        this.nameTextField.clear();
    }

}
