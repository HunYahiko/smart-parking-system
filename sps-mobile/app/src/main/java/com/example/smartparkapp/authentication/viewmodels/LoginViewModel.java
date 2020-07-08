package com.example.smartparkapp.authentication.viewmodels;

import android.net.http.HttpResponseCache;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartparkapp.authentication.AuthenticationService;
import com.example.smartparkapp.authentication.exceptions.LoginException;
import com.example.smartparkapp.dto.AuthenticationResponse;
import com.example.smartparkapp.dto.LoginDTO;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<AuthenticationResponse> responseLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginException> loginExceptionData = new MutableLiveData<>();

    private AuthenticationService authenticationService = AuthenticationService.getInstance();

    public void singIn(LoginDTO loginDTO) {
        authenticationService.singIn(loginDTO).subscribe(new SingleObserver<Response<AuthenticationResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Response<AuthenticationResponse> authenticationResponse) {
                if (authenticationResponse.code() != 404) {
                    responseLiveData.setValue(authenticationResponse.body());
                } else {
                    loginExceptionData.setValue(new LoginException("Invalid email or password", "CREDENTIALS"));
                }
            }

            @Override
            public void onError(Throwable e) {
                loginExceptionData.setValue(
                        new LoginException("Check your connection." +
                        " Contact administration if still not logging in.", "CONNECTION"));
            }
        });
    }

    public MutableLiveData<AuthenticationResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<LoginException> getLoginExceptionData() {
        return loginExceptionData;
    }
}
