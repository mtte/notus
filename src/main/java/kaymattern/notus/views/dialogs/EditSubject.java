package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.model.Subject;

public class EditSubject implements NotusController {

    @FXML private Parent root;
    @FXML private TextField nameTextField;

    private App app;
    private Subject subject;

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @FXML
    private void close() {
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void delete() {
        boolean delete = this.app.showAlert(Alert.AlertType.CONFIRMATION,
                "Löschen?",
                "Willst du wirklich das Fach " + this.subject.getName() + " löschen?",
                "Alle Noten dieses Faches werden ebenfalls gelöscht.");

        if (delete) {
            this.app.getDataAccessor().removeSubject(this.subject);
        }
        close();
    }

    @FXML
    private void edit() {
        if (! this.subject.getName().equals(this.nameTextField.getText())) {
            this.app.getDataAccessor().editSubject(this.subject, this.nameTextField.getText());
        }
        close();
    }

    @Override
    public void setUp(App app) {
        this.app = app;
    }

    @Override
    public void entered() {
        this.nameTextField.setText(this.subject.getName());
    }
}
