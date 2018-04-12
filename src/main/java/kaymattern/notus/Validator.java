package kaymattern.notus;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Validator {

    public static Pattern numberPattern = Pattern.compile("([0-9]+([.][0-9]*)?|[.][0-9]+)");

    private Map<ValidationType, List<ObservableValue>> validationEntries = new HashMap<>();

    public Validator numberValidator(StringProperty stringProperty) {
        validationEntries.computeIfAbsent(ValidationType.NUMBER, type -> new ArrayList<>());
        validationEntries.get(ValidationType.NUMBER).add(stringProperty);
        return this;
    }


    public boolean validate() {
        return this.validationEntries.get(ValidationType.NUMBER).stream().filter(this::filterValidNumbers).count() > 0;
    }


    private boolean filterValidNumbers(ObservableValue property) {
        String content = (String) property.getValue();
        return !numberPattern.matcher(content).matches();
    }

    private enum ValidationType {
        NUMBER;
    }

}
