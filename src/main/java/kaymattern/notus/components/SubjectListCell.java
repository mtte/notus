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
            return;
        }

        HBox hBox = new HBox();

        Label name = new Label(subject.getName());
        name.setFont(new Font(16));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        Label average = new Label();
        average.setFont(new Font(16));
        average.textProperty().bind(subject.average().asString("%#.2f"));

        hBox.getChildren().addAll(name, region, average);
        hBox.setPadding(new Insets(5));

        setGraphic(hBox);
    }

}
