package kaymattern.notus.validation;

import javafx.beans.value.ObservableValue;

import java.util.function.Predicate;

public enum ValidationType {
    NUMBER(ValidationType::filterValidNumbers, "Muss eine Zahl sein: '5', '1.5', etc."),
    TEXT(ValidationType::filterValidStrings, "Text darf nicht leer sein"),
    NOT_NULL(ValidationType::filterNotNull, "Wert ist nicht gültig");

    private final Predicate<ObservableValue> filterValidValues;
    private final String message;

    ValidationType(Predicate<ObservableValue> filterValidValues, String message) {
        this.filterValidValues = filterValidValues;
        this.message = message;
    }

    public Predicate<ObservableValue> filterValidValues() {
        return filterValidValues;
    }

    public String getMessage() {
        return message;
    }

    private static boolean filterNotNull(ObservableValue value) {
        return value.getValue() == null;
    }

    private static boolean filterValidNumbers(ObservableValue value) {
        String content = (String) value.getValue();
        try {
            Float.parseFloat(content);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static boolean filterValidStrings(ObservableValue value) {
        return value.getValue() == null || value.getValue().toString().isEmpty();
    }

}
