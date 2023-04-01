package dev.vladimir.cfemain.user.validation;

import org.springframework.http.HttpStatus;

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

    public HttpStatus getStatus() {
        return errors.size() > 0 ? HttpStatus.FORBIDDEN : HttpStatus.OK;
    }

    public boolean hasNoErrors() {
        return errors.size() == 0;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}