package kaymattern.notus.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.View;
import kaymattern.notus.model.Mark;
import kaymattern.notus.model.Subject;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MarkOverview implements NotusController, Initializable {

    @FXML private Label averageLabel;
    @FXML private Label titleLabel;
    @FXML private TableView<Mark> marksTable;
    @FXML private TableColumn<Mark, String> nameColumn;
    @FXML private TableColumn<Mark, LocalDate> dateColumn;
    @FXML private TableColumn<Mark, Float> markColumn;
    @FXML private TableColumn<Mark, Float> weightColumn;

    private App app;
    private Subject subject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableView();
    }

    /**
     * Sets the data of the view.
     * @param subject The subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.titleLabel.textProperty().bind(subject.nameProperty());
        this.averageLabel.textProperty().bind(subject.average().asString());
        this.marksTable.setItems(subject.getMarks());
    }

    @Override
    public void setUp(App app) {
        this.app = app;
    }

    @FXML
    private void add() {
        // TODO: show add mark dialog
    }

    @FXML
    private void back() {
        this.app.showView(View.SUBJECT_OVERVIEW);
    }

    /**
     * Initializes the columns of the marks table view.
     */
    private void initializeTableView() {
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        this.markColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty().asObject());
        this.weightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());

        // Format the date
        this.dateColumn.setCellFactory(column -> new TableCell<Mark, LocalDate>() {

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date == null || empty) {
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                setText(date.format(formatter));
            }
        });

        // Weight: format decimal value as percentage
        this.weightColumn.setCellFactory(column -> new TableCell<Mark, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    return;
                }

                setText(String.format("%.0f%%", item * 100));
            }
        });

    }

}
