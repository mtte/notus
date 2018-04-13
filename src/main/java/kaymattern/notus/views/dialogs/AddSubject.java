package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.model.Subject;
import kaymattern.notus.validation.ValidationType;
import kaymattern.notus.validation.Validator;

import java.util.Optional;

public class AddSubject implements NotusController {

    @FXML private AnchorPane root;
    @FXML private TextField nameTextField;

    private App app;
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

        String name = this.nameTextField.getText();
        this.app.getDataAccessor().createSubject(name);
        close();
        this.app.showAlert(Alert.AlertType.INFORMATION, "Status", "Fach " + name + " wurde hinzugefügt", null);
    }

    @Override
    public void setUp(App app) {
        this.app = app;

        this.validator = new Validator().register(ValidationType.TEXT, this.nameTextField.textProperty(), "Name");
    }

    @Override
    public void entered() {
        this.nameTextField.clear();
    }
}
