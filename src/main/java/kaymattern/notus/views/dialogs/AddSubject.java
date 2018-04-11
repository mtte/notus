package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.model.Subject;

public class AddSubject implements NotusController {

    @FXML private AnchorPane root;
    @FXML private TextField nameTextField;

    private App app;

    @FXML
    private void close() {
        Stage dialogStage = (Stage) root.getScene().getWindow();
        dialogStage.close();
    }

    @FXML
    private void add() {
        String name = this.nameTextField.getText();
        this.app.getDataAccessor().createSubject(name);
        close();
        // TODO: Give feedback
    }

    @Override
    public void setUp(App app) {
        this.app = app;
    }

    @Override
    public void entered() {
        this.nameTextField.clear();
    }
}
