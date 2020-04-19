package com.utm.stanislav.parkingapp.validators;

import com.utm.stanislav.parkingapp.exceptions.ValidationException;
import com.utm.stanislav.parkingapp.model.User;

@FunctionalInterface
public interface Validator<T> {
    void validate(T t) throws ValidationException;
}
