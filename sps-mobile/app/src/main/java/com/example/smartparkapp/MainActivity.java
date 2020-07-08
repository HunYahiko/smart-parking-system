package com.example.smartparkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.smartparkapp.authentication.fragments.LoginFragment;
import com.example.smartparkapp.authentication.fragments.RegistrationFragment;
import com.example.smartparkapp.http.SessionManager;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = LoginFragment.newInstance();
        fragmentTransaction.add(R.id.auth_frame, loginFragment, "LOGIN_FRAGMENT");
        fragmentTransaction.commit();

        initSessionManager();
    }

    private void initSessionManager() {
        SessionManager.getInstance(getBaseContext());
    }
}
