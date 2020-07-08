package com.example.smartparkapp.authentication.validators;

import android.util.Patterns;

import com.example.smartparkapp.authentication.exceptions.ValidationException;

public class EmailValidator implements Validator<CharSequence> {

    @Override
    public void validate(CharSequence s) throws ValidationException {
        if (!s.toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            throw new ValidationException("Invalid Email Format! e.g: xyz@xyz.com");
        }
    }
}
