package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.model.Mark;
import kaymattern.notus.model.Subject;
import kaymattern.notus.validation.ValidationType;
import kaymattern.notus.validation.Validator;

import java.util.Optional;

public class EditMark implements NotusController {

    @FXML private Parent root;
    @FXML private TextField nameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField valueTextField;
    @FXML private TextField weightTextField;

    private App app;
    private Subject subject;
    private Mark mark;
    private Validator validator;

    @FXML
    private void close() {
        Stage dialogStage = (Stage) root.getScene().getWindow();
        dialogStage.close();
    }

    @FXML
    private void edit() {
        Optional<String> validationResult = validator.validate();
        if (validationResult.isPresent()) {
            this.app.showAlert(Alert.AlertType.ERROR,
                    "Validierung fehlgeschlagen",
                    "Nicht alle Eingabefelder sind richtig ausgefüllt.",
                    validationResult.get());
            return;
        }

        if (hasChanges()) {
            this.app.getDataAccessor().editMark(this.subject,
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
        boolean delete = this.app.showAlert(Alert.AlertType.CONFIRMATION,
                "Löschen?",
                "Willst du wirklich die Note " + this.mark.getName() + " aus dem Fach " + this.subject.getName() + " löschen?",
                null);

        if (delete) {
            this.app.getDataAccessor().removeMark(this.subject, this.mark);
            close();
        }
    }

    /**
     * Set the data to edit.
     * @param subject The subject
     * @param mark The mark to edit
     */
    public void setData(Subject subject, Mark mark) {
        this.subject = subject;
        this.mark = mark;
    }

    @Override
    public void setUp(App app) {
        this.app = app;

        this.validator = new Validator()
                .register(ValidationType.TEXT, this.nameTextField.textProperty(), "Name")
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
