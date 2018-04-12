package kaymattern.notus.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model class to represent a subject.
 */
public class Subject {

    private final int id;
    private StringProperty name;
    private ObservableList<Mark> marks = FXCollections.observableArrayList(mark -> new Observable[] { mark.valueProperty(), mark.weightProperty() });

    /**
     * Construnctor.
     * @param name The name of the subject
     */
    public Subject(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }

    public DoubleBinding average() {
        return Bindings.createDoubleBinding(() -> {
            double sum = this.marks.stream().mapToDouble(mark -> mark.getValue() * mark.getWeight()).sum();
            double sum2 = this.marks.stream().mapToDouble(Mark::getWeight).sum();
            return sum / sum2;
        }, this.marks);
    }

    // Getter & Setter

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 100) {
            throw new IllegalArgumentException("Length of name cannot be larger than 100");
        }
        this.name.set(name);
    }

    public ObservableList<Mark> getMarks() {
        return marks;
    }

}
