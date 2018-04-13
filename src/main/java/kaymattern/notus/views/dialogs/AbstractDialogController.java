package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kaymattern.notus.validation.Validator;
import kaymattern.notus.views.AbstractNotusController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class AbstractDialogController extends AbstractNotusController {

    @FXML private Parent root;

    private Validator validator;

    @FXML
    protected void close() {
        Stage dialogStage = (Stage) this.root.getScene().getWindow();
        dialogStage.close();
    }

    @Override
    protected void init() {
        this.validator = new Validator();
        initValidator(this.validator);
    }

    /**
     * Initializes the validator.
     * @param validator The validator of the UI
     */
    protected abstract void initValidator(Validator validator);

    /**
     * Validates the UI.
     * Shows an info dialog if UI has to be corrected
     * @return If the validation has been successful
     */
    protected boolean validateUi() {
        Optional<String> validationResult = validator.validate();
        if (validationResult.isPresent()) {
            getApp().showAlert(Alert.AlertType.INFORMATION,
                    "Validierung fehlgeschlagen",
                    "Nicht alle Eingabefelder sind richtig ausgefuellt.",
                    validationResult.get());
            return false;
        }
        return true;
    }

}
