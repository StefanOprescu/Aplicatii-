package me.map.lab3.validator;

import me.map.lab3.exceptii.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}