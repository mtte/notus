package kaymattern.notus.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Mark {

    private final int id;
    private StringProperty name;
    private ObjectProperty<LocalDate> date;
    private FloatProperty value;
    private FloatProperty weight;

    public Mark(int id, String name, LocalDate date, float value, float weight) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
        this.value = new SimpleFloatProperty(value);
        this.weight = new SimpleFloatProperty(weight);
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

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public float getValue() {
        return value.get();
    }

    public FloatProperty valueProperty() {
        return value;
    }

    public void setValue(float value) {
        this.value.set(value);
    }

    public float getWeight() {
        return weight.get();
    }

    public FloatProperty weightProperty() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight.set(weight);
    }

}
