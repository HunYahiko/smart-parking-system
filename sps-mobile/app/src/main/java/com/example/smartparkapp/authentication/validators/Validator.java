package com.example.smartparkapp.authentication.validators;

import com.example.smartparkapp.authentication.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T t) throws ValidationException;
}
