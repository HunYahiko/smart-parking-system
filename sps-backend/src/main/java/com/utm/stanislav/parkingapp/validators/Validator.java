package com.utm.stanislav.parkingapp.validators;

import com.utm.stanislav.parkingapp.model.exceptions.ValidationException;

@FunctionalInterface
public interface Validator<T> {
    void validate(T t) throws ValidationException;
}
