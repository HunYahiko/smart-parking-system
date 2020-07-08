package com.example.smartparkapp.authentication.validators;

import com.example.smartparkapp.authentication.exceptions.ValidationException;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class RepeatPasswordValidator implements Validator<CharSequence> {

    private WeakReference<TextInputEditText> actualPasswordTextReference;

    public RepeatPasswordValidator(WeakReference<TextInputEditText> actualPasswordText) {
        this.actualPasswordTextReference = actualPasswordText;
    }

    @Override
    public void validate(CharSequence repeatPassword) throws ValidationException {
        String actualPassword = Objects.requireNonNull(this.actualPasswordTextReference.get().getText()).toString();
        if (!actualPassword.contentEquals(repeatPassword)) {
            throw new ValidationException("Repeat password does not match!");
        }
    }
}
