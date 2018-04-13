package kaymattern.notus.views;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MarkOverview extends AbstractNotusController {

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
    @FXML private LineChart<String, Float> lineChart;
    @FXML private CategoryAxis categoryAxis;
    @FXML private PieChart markDistribution;
    @FXML private PieChart weightDistribution;

    private Subject subject;

    @Override
    protected void init() {
        container.prefWidthProperty().bind(root.widthProperty());
        content.prefWidthProperty().bind(container.widthProperty());
        markDistribution.prefWidthProperty().bind(container.widthProperty().divide(2));
        weightDistribution.prefWidthProperty().bind(container.widthProperty().divide(2));

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

    @FXML
    private void add() {
        AddMark addMarkController = (AddMark) getApp().getControllerOfView(View.ADD_MARK);
        addMarkController.setSubject(this.subject);

        getApp().showView(View.ADD_MARK);
    }

    @FXML
    private void back() {
        getApp().showView(View.SUBJECT_OVERVIEW);
    }

    /**
     * Show edit mark dialog.
     * @param mark the mark to edit
     */
    private void edit(Mark mark) {
        EditMark editMarkController = (EditMark) getApp().getControllerOfView(View.EDIT_MARK);
        editMarkController.setData(this.subject, mark);

        getApp().showView(View.EDIT_MARK);
    }

    @Override
    public void entered() {
        drawCharts();
        this.subject.getMarks().addListener((InvalidationListener) event -> drawCharts());
    }

    /**
     * Draws the charts
     */
    private void drawCharts() {
        // clear
        this.lineChart.getData().clear();
        this.categoryAxis.getCategories().clear();
        this.markDistribution.getData().clear();
        this.weightDistribution.getData().clear();

        // line chart
        this.categoryAxis.setCategories(this.subject.getMarks()
                .stream()
                .sorted(Comparator.comparing(Mark::getDate))
                .map(Mark::getName)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        XYChart.Series<String, Float> series = new XYChart.Series<>();
        series.getData().addAll(this.subject.getMarks()
                .sorted(Comparator.comparing(Mark::getDate))
                .stream()
                .map(mark -> new XYChart.Data<>(mark.getName(), mark.getValue()))
                .collect(Collectors.toList()));
        this.lineChart.getData().add(series);

        // mark distribution
        this.markDistribution.setData(this.subject.getMarks()
                .stream()
                .collect(Collectors.groupingBy(Mark::getValue))
                .entrySet()
                .stream()
                .map(entry -> new PieChart.Data(String.valueOf(entry.getKey()), entry.getValue().size()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        this.markDistribution.setLabelLineLength(10);

        // weight distribution
        this.weightDistribution.setData(this.subject.getMarks()
                .stream()
                .collect(Collectors.groupingBy(Mark::getWeight))
                .entrySet()
                .stream()
                .map(entry -> new PieChart.Data(String.valueOf(entry.getKey()), entry.getValue().size()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        this.weightDistribution.setLabelLineLength(10);
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
