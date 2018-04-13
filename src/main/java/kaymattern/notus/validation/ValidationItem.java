package kaymattern.notus.validation;

import javafx.beans.value.ObservableValue;

class ValidationItem {
    private final ValidationType type;
    private final ObservableValue value;
    private final String name;

    protected ValidationItem(ValidationType type, ObservableValue value, String name) {
        this.type = type;
        this.value = value;
        this.name = name;
    }

    protected String validate() {
        if (this.type.filterValidValues().test(this.value)) {
            return name + ": " + type.getMessage();
        }
        return null;
    }

}
