package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditMark {

    @FXML private TextField nameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField markTextField;
    @FXML private TextField weightTextField;

    @FXML
    private void cancel() {

    }

    @FXML
    private void add() {
        validateFields();
    }

    @FXML
    private void delete() {

    }

    private void validateFields() {
        boolean name = !nameTextField.getText().isEmpty();
        markTextField.getText();
    }

}
