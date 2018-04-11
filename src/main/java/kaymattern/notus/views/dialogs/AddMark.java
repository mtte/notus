package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.model.Subject;


public class AddMark implements NotusController {

    @FXML private Parent root;
    @FXML private TextField nameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField markTextField;
    @FXML private TextField weightTextField;

    private App app;
    private Subject subject;

    @FXML
    private void close() {
        Stage dialogStage = (Stage) root.getScene().getWindow();
        dialogStage.close();
    }

    @FXML
    private void add() {
        // TODO: Show add mark dialog
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void setUp(App app) {
        this.app = app;
    }

    @Override
    public void entered() {
        nameTextField.clear();
        datePicker.getEditor().clear();
        markTextField.clear();
        weightTextField.clear();
    }

}
