package me.validator;

import me.exceptii.ValidationException;

public interface Validator<E> {
    void validate(E e) throws ValidationException;
}
