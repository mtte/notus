package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import kaymattern.notus.model.Mark;
import kaymattern.notus.model.Subject;
import kaymattern.notus.validation.ValidationType;
import kaymattern.notus.validation.Validator;

public class EditMark extends AbstractDialogController {

    @FXML private TextField nameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField valueTextField;
    @FXML private TextField weightTextField;

    private Subject subject;
    private Mark mark;

    /**
     * Set the data to edit.
     * @param subject The subject
     * @param mark The mark to edit
     */
    public void setData(Subject subject, Mark mark) {
        this.subject = subject;
        this.mark = mark;
    }

    @FXML
    private void edit() {
        if (! validateUi()) {
            return;
        }

        if (hasChanges()) {
            getApp().getDataAccessor().editMark(this.subject,
                    this.mark,
                    this.nameTextField.getText(),
                    this.datePicker.getValue(),
                    Float.parseFloat(this.valueTextField.getText()),
                    Float.parseFloat(this.weightTextField.getText()));
        }
        close();
    }

    /**
     * Checks if the input has been modified.
     * @return If the user changed values
     */
    private boolean hasChanges() {
        return !(this.nameTextField.getText().equals(this.mark.getName())
                && this.datePicker.getValue().equals(this.mark.getDate())
                && this.valueTextField.getText().equals(String.valueOf(this.mark.getValue()))
                && this.weightTextField.getText().equals(String.valueOf(this.mark.getWeight())));
    }

    @FXML
    private void delete() {
        boolean delete = getApp().showAlert(Alert.AlertType.CONFIRMATION,
                "Löschen?",
                "Willst du wirklich die Note " + this.mark.getName() + " aus dem Fach " + this.subject.getName() + " löschen?",
                null);

        if (delete) {
            getApp().getDataAccessor().removeMark(this.subject, this.mark);
            close();
        }
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
        this.nameTextField.setText(this.mark.getName());
        this.datePicker.setValue(this.mark.getDate());
        this.valueTextField.setText(String.valueOf(this.mark.getValue()));
        this.weightTextField.setText(String.valueOf(this.mark.getWeight()));
    }

}
