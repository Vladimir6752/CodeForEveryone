package dev.vladimir.cfemain.user.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationObject<T> {
    private final List<String> errors = new ArrayList<>();
    private T value;

    public void addError(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasNoErrors() {
        return errors.isEmpty();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}