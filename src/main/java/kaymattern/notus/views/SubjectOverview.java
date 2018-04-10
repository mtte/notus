package kaymattern.notus.views;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.View;
import kaymattern.notus.components.SubjectListCell;
import kaymattern.notus.model.Subject;

public class SubjectOverview implements NotusController {

    private App app;

    @FXML private ListView<Subject> subjectList;

    /**
     * Shows the add subject dialog.
     */
    @FXML
    private void add() {
        app.showView(View.ADD_SUBJECT);
    }

    @Override
    public void setUp(App app) {
        this.app = app;

        this.initList();
    }

    /**
     * Initialize the list view.
     */
    private void initList() {
        this.subjectList.setItems(app.getDataAccessor().getSubjects());
        this.subjectList.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                // only when double clicked
                openMarkView();
            }
        });
        this.subjectList.setCellFactory(l -> new SubjectListCell());
    }

    /**
     * Opens the mark overview of the currently selected subject.
     */
    private void openMarkView() {
        MarkOverview controller = (MarkOverview) this.app.getControllerOfView(View.MARK_OVERVIEW);
        // TODO: Set the currently selected subject

        this.app.showView(View.MARK_OVERVIEW);
    }

}
