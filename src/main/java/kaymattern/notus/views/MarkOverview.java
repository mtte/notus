package kaymattern.notus.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import kaymattern.notus.App;
import kaymattern.notus.NotusController;
import kaymattern.notus.View;
import kaymattern.notus.model.Mark;
import kaymattern.notus.model.Subject;
import kaymattern.notus.views.dialogs.AddMark;
import kaymattern.notus.views.dialogs.EditMark;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MarkOverview implements NotusController, Initializable {

    @FXML private Region root;
    @FXML private Region content;
    @FXML private Region container;

    @FXML private Label averageLabel;
    @FXML private Label titleLabel;
    @FXML private TableView<Mark> marksTable;
    @FXML private TableColumn<Mark, String> nameColumn;
    @FXML private TableColumn<Mark, LocalDate> dateColumn;
    @FXML private TableColumn<Mark, Float> markColumn;
    @FXML private TableColumn<Mark, Float> weightColumn;
    @FXML private LineChart<String, Integer> lineChart;
    @FXML private PieChart markDistribution;
    @FXML private PieChart weightDistribution;

    private App app;
    private Subject subject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        container.prefWidthProperty().bind(root.widthProperty());
        content.prefWidthProperty().bind(container.widthProperty());

        initializeTableView();
    }

    /**
     * Sets the data of the view.
     * @param subject The subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.titleLabel.textProperty().bind(subject.nameProperty());
        this.averageLabel.textProperty().bind(subject.average().asString("%#.2f"));
        this.marksTable.setItems(subject.getMarks());
    }

    @Override
    public void setUp(App app) {
        this.app = app;
    }

    @FXML
    private void add() {
        AddMark addMarkController = (AddMark) this.app.getControllerOfView(View.ADD_MARK);
        addMarkController.setSubject(this.subject);

        this.app.showView(View.ADD_MARK);
    }

    @FXML
    private void back() {
        this.app.showView(View.SUBJECT_OVERVIEW);
    }

    private void edit(Mark mark) {
        EditMark editMarkController = (EditMark) this.app.getControllerOfView(View.EDIT_MARK);
        editMarkController.setData(this.subject, mark);

        this.app.showView(View.EDIT_MARK);
    }

    /**
     * Returns the current selected mark or null if there is none.
     * @return current selected mark
     */
    private Mark getMark() {
        return this.marksTable.getSelectionModel().getSelectedItem();
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
                    setText("");
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
                    setText("");
                    return;
                }

                setText(String.format("%.0f%%", item * 100));
            }
        });

        this.marksTable.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2 && getMark() != null) {
                edit(getMark());
            }
        });

    }

}
