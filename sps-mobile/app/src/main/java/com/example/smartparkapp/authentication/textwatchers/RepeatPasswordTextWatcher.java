package com.example.smartparkapp.authentication.textwatchers;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.smartparkapp.authentication.exceptions.ValidationException;
import com.example.smartparkapp.authentication.validators.RepeatPasswordValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;

public class RepeatPasswordTextWatcher implements TextWatcher {

    private WeakReference<TextInputLayout> repeatPasswordInputLayoutReference;
    private RepeatPasswordValidator repeatPasswordValidator;

    public RepeatPasswordTextWatcher(WeakReference<TextInputLayout> repeatPasswordInputLayoutReference,
                                     RepeatPasswordValidator repeatPasswordValidator) {
        this.repeatPasswordInputLayoutReference = repeatPasswordInputLayoutReference;
        this.repeatPasswordValidator = repeatPasswordValidator;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            this.repeatPasswordValidator.validate(s);
            this.repeatPasswordInputLayoutReference.get().setError(null);
        } catch (ValidationException ex) {
            this.repeatPasswordInputLayoutReference.get().setError(ex.getMessage());
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
