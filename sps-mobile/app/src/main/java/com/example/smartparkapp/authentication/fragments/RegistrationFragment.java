package com.example.smartparkapp.authentication.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartparkapp.R;
import com.example.smartparkapp.authentication.textwatchers.EmailTextWatcher;
import com.example.smartparkapp.authentication.textwatchers.RepeatPasswordTextWatcher;
import com.example.smartparkapp.authentication.validators.EmailValidator;
import com.example.smartparkapp.authentication.validators.RepeatPasswordValidator;
import com.example.smartparkapp.authentication.viewmodels.RegistrationViewModel;
import com.example.smartparkapp.dto.SingUpExceptionDTO;
import com.example.smartparkapp.dto.UserDTO;
import com.example.smartparkapp.model.enums.ValueLessResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class RegistrationFragment extends Fragment {

    private static final String FRAGMENT_TAG = "REGISTRATION_FRAGMENT";

    private RegistrationViewModel mViewModel;
    private TextInputLayout emailInputLayout;
    private TextInputEditText emailInputText;
    private TextInputLayout repeatPasswordInputLayout;
    private TextInputEditText repeatPasswordInputText;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText passwordInputText;
    private TextInputLayout usernameInputLayout;
    private TextInputEditText usernameInputText;
    private TextInputLayout phoneNumberInputLayout;
    private TextInputEditText phoneNumberInputText;
    private TextInputLayout firstNameInputLayout;
    private TextInputEditText firstNameInputText;
    private TextInputLayout lastNameInputLayout;
    private TextInputEditText lastNameInputText;
    private MaterialButton registrationButton;

    private FragmentManager fragmentManager;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.registration_fragment, container, false);
        findViews(view);
        addTextWatchers();

        this.registrationButton.setOnClickListener((v -> singUp()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        mViewModel.getSignUpExceptionData().observe(this, this::handleRegistrationException);
        mViewModel.getResponseLiveData().observe(this, this::handleSuccessfulRegistration);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    private void handleSuccessfulRegistration(ValueLessResponse response) {
        registrationButton.setEnabled(Boolean.TRUE);
        Toast.makeText(getContext(), "User successfully created", Toast.LENGTH_LONG).show();
        showSignInFragment();
    }

    private void handleRegistrationException(SingUpExceptionDTO exception) {
        registrationButton.setEnabled(Boolean.TRUE);
        findViewAndSetMessage(exception);
        Toast.makeText(getContext(), exception.getExceptionMessage(), Toast.LENGTH_LONG).show();
    }

    private void findViewAndSetMessage(SingUpExceptionDTO exceptionDTO) {
        switch(exceptionDTO.getInputField()) {
            case USERNAME:
                usernameInputLayout.setError(exceptionDTO.getExceptionMessage());
                break;
            case PHONE_NUMBER:
                phoneNumberInputLayout.setError(exceptionDTO.getExceptionMessage());
                break;
            default:
                break;
        }
    }

    private void findViews(View view) {
        this.emailInputLayout = view.findViewById(R.id.email_input_layout);
        this.emailInputText = view.findViewById(R.id.input_email);

        this.passwordInputLayout = view.findViewById(R.id.password_input_layout);
        this.passwordInputText = view.findViewById(R.id.input_password);

        this.repeatPasswordInputLayout = view.findViewById(R.id.repeat_password_input_layout);
        this.repeatPasswordInputText = view.findViewById(R.id.input_repeat_password);

        this.usernameInputLayout = view.findViewById(R.id.username_input_layout);
        this.usernameInputText = view.findViewById(R.id.input_username);

        this.firstNameInputLayout = view.findViewById(R.id.first_name_input_layout);
        this.firstNameInputText = view.findViewById(R.id.input_first_name);

        this.lastNameInputLayout = view.findViewById(R.id.last_name_input_layout);
        this.lastNameInputText = view.findViewById(R.id.input_last_name);

        this.phoneNumberInputLayout = view.findViewById(R.id.phone_number_input_layout);
        this.phoneNumberInputText = view.findViewById(R.id.input_phone_number);

        this.registrationButton = view.findViewById(R.id.registration_button);
    }

    private void addTextWatchers() {
        addEmailWatcher();
        addRepeatedPasswordWatcher();
    }

    private void addEmailWatcher() {
        WeakReference<TextInputLayout> emailInputLayoutReference = new WeakReference<>(this.emailInputLayout);
        EmailTextWatcher emailTextWatcher = new EmailTextWatcher(emailInputLayoutReference, new EmailValidator());
        this.emailInputText.addTextChangedListener(emailTextWatcher);
    }

    private void addRepeatedPasswordWatcher() {
        WeakReference<TextInputLayout> repeatPasswordInputLayoutReference =
                new WeakReference<>(this.repeatPasswordInputLayout);

        WeakReference<TextInputEditText> passwordInputText =
                new WeakReference<>(this.passwordInputText);

        RepeatPasswordValidator repeatPasswordValidator = new RepeatPasswordValidator(passwordInputText);

        RepeatPasswordTextWatcher repeatPasswordTextWatcher =
                new RepeatPasswordTextWatcher(repeatPasswordInputLayoutReference, repeatPasswordValidator);

        this.repeatPasswordInputText.addTextChangedListener(repeatPasswordTextWatcher);
    }

    private void singUp() {
        boolean isFormValid = true;

        String email = Objects.requireNonNull(emailInputText.getText()).toString();
        if (emailInputLayout.getError() != null) {
            isFormValid = false;
        }

        if (email.isEmpty()) {
            emailInputLayout.setError("You must fill this field");
            isFormValid = false;
        }

        String username = Objects.requireNonNull(usernameInputText.getText()).toString();
        if (username.isEmpty()) {
            usernameInputLayout.setError("You must fill this field");
            isFormValid = false;
        } else {
            usernameInputLayout.setError(null);
        }

        String firstName = Objects.requireNonNull(firstNameInputText.getText()).toString();
        if (firstName.isEmpty()) {
            firstNameInputLayout.setError("You must fill this field");
            isFormValid = false;
        } else {
            firstNameInputLayout.setError(null);
        }

        String lastName = Objects.requireNonNull(lastNameInputText.getText()).toString();
        if (lastName.isEmpty()) {
            lastNameInputLayout.setError("You must fill this field");
            isFormValid = false;
        } else {
            lastNameInputLayout.setError(null);
        }

        String phoneNumber = Objects.requireNonNull(phoneNumberInputText.getText()).toString();
        if (phoneNumber.isEmpty()) {
            phoneNumberInputLayout.setError("You must fill this field!");
            isFormValid = false;
        } else {
            phoneNumberInputLayout.setError(null);
        }

        String password = Objects.requireNonNull(passwordInputText.getText()).toString();
        if (password.isEmpty()) {
            passwordInputLayout.setError("You must fill this field!");
            isFormValid = false;
        } else {
            passwordInputLayout.setError(null);
        }

        String repeatPassword = Objects.requireNonNull(repeatPasswordInputText.getText()).toString();
        if (repeatPasswordInputLayout.getError() != null) {
            isFormValid = false;
        }

        if (repeatPassword.isEmpty()) {
            repeatPasswordInputLayout.setError("You must fill this field!");
            isFormValid = false;
        }

        if (isFormValid) {
            UserDTO userDTO = new UserDTO(firstName, lastName, username, email, password, phoneNumber);
            mViewModel.signUp(userDTO);
            registrationButton.setEnabled(Boolean.FALSE);
        }
    }

    private void showSignInFragment() {
        Fragment registrationFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        Fragment loginFragment = fragmentManager.findFragmentByTag("LOGIN_FRAGMENT");
        if (loginFragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.auth_frame, loginFragment)
                    .hide(registrationFragment)
                    .commit();

        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            loginFragment = LoginFragment.newInstance();
            fragmentTransaction
                    .add(R.id.auth_frame, loginFragment, "LOGIN_FRAGMENT")
                    .hide(registrationFragment)
                    .commit();
        }
    }

}
