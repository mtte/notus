package kaymattern.notus.views;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.validation.Validator;
import kaymattern.notus.View;
import kaymattern.notus.components.SubjectListCell;
import kaymattern.notus.model.Subject;
import kaymattern.notus.views.dialogs.EditSubject;

public class SubjectOverview implements NotusController {

    private App app;

    @FXML private ListView<Subject> subjectList;

    /**
     * Shows the add subject dialog.
     */
    @FXML
    private void add() {
        this.app.showView(View.ADD_SUBJECT);
    }

    /**
     * Shows the edit subject dialog.
     */
    @FXML
    private void edit() {
        if (this.getSubject() != null) {
            EditSubject editSubjectController = (EditSubject) this.app.getControllerOfView(View.EDIT_SUBJECT);
            editSubjectController.setSubject(this.getSubject());

            this.app.showView(View.EDIT_SUBJECT);
        }
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
            if (event.getClickCount() >= 2 && this.getSubject() != null) {
                // only when double clicked
                openMarkView(this.getSubject());
            }
        });
        this.subjectList.setCellFactory(l -> new SubjectListCell());
    }

    /**
     * Opens the mark overview of the currently selected subject.
     */
    private void openMarkView(Subject subject) {
        MarkOverview controller = (MarkOverview) this.app.getControllerOfView(View.MARK_OVERVIEW);
        controller.setSubject(subject);

        this.app.showView(View.MARK_OVERVIEW);
    }

    /**
     * Returns the current selected subject or null if there is none.
     * @return current selected subject
     */
     private Subject getSubject() {
         return this.subjectList.getSelectionModel().getSelectedItem();
     }

}
