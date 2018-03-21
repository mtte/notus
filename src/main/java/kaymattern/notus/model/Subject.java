package kaymattern.notus.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class to represent a subject.
 */
public class Subject {

    private StringProperty name;

    /**
     * Construnctor.
     * @param name The name of the subject
     */
    public Subject(String name) {
        this.name = new SimpleStringProperty(name);
    }

    // JavaFX Properties

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
