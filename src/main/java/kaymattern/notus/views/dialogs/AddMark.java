package kaymattern.notus.views.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;


public class AddMark implements NotusController {

    @FXML private TextField nameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField markTextField;
    @FXML private TextField weightTextField;

    private App app;

    @FXML
    private void cancel() {

    }

    @FXML
    private void add() {

    }

    @Override
    public void setUp(App app) {
        this.app = app;
    }
}
