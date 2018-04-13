package kaymattern.notus.validation;

import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Validator {

    private List<ValidationItem> validationItems = new ArrayList<>();

    public Validator register(ValidationType type, ObservableValue value, String name) {
        this.validationItems.add(new ValidationItem(type, value, name));
        return this;
    }

    public Optional<String> validate() {
        String result = this.validationItems.stream()
                .map(ValidationItem::validate)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(System.lineSeparator()));
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

}
