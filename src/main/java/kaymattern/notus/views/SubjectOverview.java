package kaymattern.notus.views;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import kaymattern.notus.View;
import kaymattern.notus.components.SubjectListCell;
import kaymattern.notus.model.Subject;
import kaymattern.notus.views.dialogs.EditSubject;

public class SubjectOverview extends AbstractNotusController {

    @FXML private ListView<Subject> subjectList;

    @Override
    protected void init() {
        this.subjectList.setItems(getApp().getDataAccessor().getSubjects());
        this.subjectList.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2 && this.getSubject() != null) {
                // only when double clicked
                openMarkView(this.getSubject());
            }
        });
        this.subjectList.setCellFactory(l -> new SubjectListCell());
    }

    /**
     * Shows the add subject dialog.
     */
    @FXML
    private void add() {
        getApp().showView(View.ADD_SUBJECT);
    }

    /**
     * Shows the edit subject dialog.
     */
    @FXML
    private void edit() {
        if (this.getSubject() != null) {
            EditSubject editSubjectController = (EditSubject) getApp().getControllerOfView(View.EDIT_SUBJECT);
            editSubjectController.setSubject(this.getSubject());

            getApp().showView(View.EDIT_SUBJECT);
        }
    }

    /**
     * Opens the mark overview of the currently selected subject.
     */
    private void openMarkView(Subject subject) {
        MarkOverview controller = (MarkOverview) getApp().getControllerOfView(View.MARK_OVERVIEW);
        controller.setSubject(subject);

        getApp().showView(View.MARK_OVERVIEW);
    }

    /**
     * Returns the current selected subject or null if there is none.
     * @return current selected subject
     */
     private Subject getSubject() {
         return this.subjectList.getSelectionModel().getSelectedItem();
     }

}
