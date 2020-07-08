package com.example.smartparkapp.authentication.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparkapp.R;
import com.example.smartparkapp.authentication.exceptions.LoginException;
import com.example.smartparkapp.authentication.viewmodels.LoginViewModel;
import com.example.smartparkapp.dashboard.DashboardActivity;
import com.example.smartparkapp.dto.AuthenticationResponse;
import com.example.smartparkapp.dto.LoginDTO;
import com.example.smartparkapp.http.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private static final String FRAGMENT_TAG = "LOGIN_FRAGMENT";


    private LoginViewModel mViewModel;
    private FragmentManager fragmentManager;
    private TextInputEditText usernameInputText;
    private TextInputLayout usernameInputLayout;
    private TextInputEditText passwordInputText;
    private TextInputLayout passwordInputLayout;
    private TextView singUpText;
    private MaterialButton loginButton;

    private SharedPreferences sharedPreferences;
    private static final String TOKEN_PREFERENCES = "TOKEN_REPO";
    private static final String TOKEN_KEY = "TOKEN";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);
        findViews(view);
        singUpText.setOnClickListener((v -> showSignUpFragment()));
        loginButton.setOnClickListener((v -> singIn()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mViewModel.getResponseLiveData().observe(this, this::handleSuccessfulLogin);
        mViewModel.getLoginExceptionData().observe(this, this::handleLoginException);
    }

    private void findViews(View view) {
        singUpText = view.findViewById(R.id.sign_up_text);
        usernameInputText = view.findViewById(R.id.input_username);
        passwordInputText = view.findViewById(R.id.input_password);
        usernameInputLayout = view.findViewById(R.id.username_input_layout);
        passwordInputLayout = view.findViewById(R.id.password_input_layout);
        loginButton = view.findViewById(R.id.login_button);
    }


    private void singIn() {
        String username = Objects.requireNonNull(usernameInputText.getText()).toString();
        String password = Objects.requireNonNull(passwordInputText.getText()).toString();

        if (username.isEmpty()) {
            usernameInputLayout.setError("You must fill this field!");
        }
        if (password.isEmpty()) {
            passwordInputLayout.setError("You must fill this field!");
        }

        if (!username.isEmpty() && !password.isEmpty()) {
            usernameInputLayout.setError(null);
            passwordInputLayout.setError(null);
            mViewModel.singIn(new LoginDTO(username, password));
        }
    }

    private void handleSuccessfulLogin(AuthenticationResponse authenticationResponse) {
        Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_LONG).show();
        persistToken(authenticationResponse.getToken());
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();

        Log.i("BEARER_TOKEN", authenticationResponse.getToken());
    }

    private void persistToken(String token) {
        SessionManager.getInstance().setAuthToken(token);
    }

    private void handleLoginException(LoginException exception) {
        if (exception.getContext().equals("CONNECTION")) {
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            usernameInputLayout.setError(exception.getMessage());
            passwordInputLayout.setError(exception.getMessage());
        }
    }

    private void showSignUpFragment() {
        Fragment registrationFragment = fragmentManager.findFragmentByTag("REGISTRATION_FRAGMENT");
        Fragment loginFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (registrationFragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.auth_frame, registrationFragment)
                    .hide(loginFragment)
                    .addToBackStack(FRAGMENT_TAG)
                    .commit();

        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            registrationFragment = RegistrationFragment.newInstance();
            fragmentTransaction
                    .add(R.id.auth_frame, registrationFragment, "REGISTRATION_FRAGMENT")
                    .hide(loginFragment)
                    .addToBackStack(FRAGMENT_TAG)
                    .commit();
        }
    }

}
