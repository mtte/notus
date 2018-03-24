package kaymattern.notus.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class to represent a subject.
 */
public class Subject {

    private final IntegerProperty id;
    private StringProperty name;

    /**
     * Construnctor.
     * @param name The name of the subject
     */
    public Subject(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    // JavaFX Properties

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
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
}
