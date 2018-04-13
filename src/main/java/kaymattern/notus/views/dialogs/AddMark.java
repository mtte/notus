package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.model.Subject;
import kaymattern.notus.validation.ValidationType;
import kaymattern.notus.validation.Validator;

import java.time.LocalDate;
import java.util.Optional;


public class AddMark implements NotusController {

    @FXML private Parent root;
    @FXML private TextField nameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField valueTextField;
    @FXML private TextField weightTextField;

    private App app;
    private Subject subject;
    private Validator validator;

    @FXML
    private void close() {
        Stage dialogStage = (Stage) root.getScene().getWindow();
        dialogStage.close();
    }

    @FXML
    private void add() {
        Optional<String> validationResult = validator.validate();
        if (validationResult.isPresent()) {
            this.app.showAlert(Alert.AlertType.ERROR,
                    "Validierung fehlgeschlagen",
                    "Nicht alle Eingabefelder sind richtig ausgefüllt.",
                    validationResult.get());
            return;
        }

        String name = nameTextField.getText();
        LocalDate date = datePicker.getValue();
        float value = Float.parseFloat(valueTextField.getText());
        float weight = Float.parseFloat(weightTextField.getText());

        this.app.getDataAccessor().createMark(this.subject, name, date, value, weight);
        close();
        this.app.showAlert(Alert.AlertType.INFORMATION, "Status", "Note wurde hinzugefügt.", null);
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
        nameTextField.clear();
        datePicker.getEditor().clear();
        valueTextField.clear();
        weightTextField.clear();
    }

}
