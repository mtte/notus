package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import kaymattern.notus.model.Subject;
import kaymattern.notus.validation.ValidationType;
import kaymattern.notus.validation.Validator;

import java.time.LocalDate;


public class AddMark extends AbstractDialogController {

    @FXML private TextField nameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField valueTextField;
    @FXML private TextField weightTextField;

    private Subject subject;

    /**
     * Sets the current subject
     * @param subject the subject of the mark
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @FXML
    private void add() {
        if (! validateUi()) {
            return;
        }

        String name = nameTextField.getText();
        LocalDate date = datePicker.getValue();
        float value = Float.parseFloat(valueTextField.getText());
        float weight = Float.parseFloat(weightTextField.getText());

        getApp().getDataAccessor().createMark(this.subject, name, date, value, weight);
        close();
        getApp().showAlert(Alert.AlertType.INFORMATION, "Status", "Note wurde hinzugefügt.", null);
    }

    @Override
    protected void initValidator(Validator validator) {
        validator.register(ValidationType.TEXT, this.nameTextField.textProperty(), "Name")
                .register(ValidationType.NOT_NULL, this.datePicker.valueProperty(), "Datum")
                .register(ValidationType.TEXT, this.valueTextField.textProperty(), "Note")
                .register(ValidationType.NUMBER, this.valueTextField.textProperty(), "Note")
                .register(ValidationType.TEXT, this.weightTextField.textProperty(), "Gewichtung")
                .register(ValidationType.NUMBER, this.weightTextField.textProperty(), "Gewichtung");
    }

    @Override
    public void entered() {
        nameTextField.clear();
        datePicker.getEditor().clear();
        valueTextField.clear();
        weightTextField.clear();
    }

}
