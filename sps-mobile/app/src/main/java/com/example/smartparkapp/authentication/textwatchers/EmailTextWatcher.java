package com.example.smartparkapp.authentication.textwatchers;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.smartparkapp.authentication.exceptions.ValidationException;
import com.example.smartparkapp.authentication.validators.Validator;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;

public class EmailTextWatcher implements TextWatcher {

    private WeakReference<TextInputLayout> emailInputLayout;
    private Validator<? super CharSequence> emailValidator;

    public EmailTextWatcher(WeakReference<TextInputLayout> emailInputLayout,
                            Validator<? super CharSequence> emailValidator) {
        this.emailInputLayout = emailInputLayout;
        this.emailValidator = emailValidator;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            this.emailValidator.validate(s);
            this.emailInputLayout.get().setError(null);
        } catch (ValidationException ex) {
            this.emailInputLayout.get().setError(ex.getMessage());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
