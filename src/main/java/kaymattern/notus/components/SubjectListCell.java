package kaymattern.notus.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import kaymattern.notus.model.Subject;

public class SubjectListCell extends ListCell<Subject> {
    @Override
    protected void updateItem(Subject subject, boolean empty) {
        super.updateItem(subject, empty);

        // Only use the custom layout if there is a subject present
        if (subject == null) {
            setGraphic(null);
            return;
        }

        HBox hBox = new HBox();

        Label name = new Label();
        name.textProperty().bind(subject.nameProperty());
        name.setFont(new Font(16));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        Label average = new Label();
        average.textProperty().bind(subject.average().asString("%#.2f"));
        average.setFont(new Font(16));

        hBox.getChildren().addAll(name, region, average);
        hBox.setPadding(new Insets(5));

        setGraphic(hBox);
    }

}
