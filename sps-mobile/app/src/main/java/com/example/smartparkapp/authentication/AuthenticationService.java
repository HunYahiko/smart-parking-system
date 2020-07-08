package com.example.smartparkapp.authentication;

import com.example.smartparkapp.dto.AuthenticationResponse;
import com.example.smartparkapp.dto.LoginDTO;
import com.example.smartparkapp.dto.SingUpExceptionDTO;
import com.example.smartparkapp.dto.UserDTO;
import com.example.smartparkapp.http.api.AuthAPI;
import com.example.smartparkapp.http.client.SPSClient;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {

    private AuthAPI authAPI;
    private static AuthenticationService authenticationService;

    private AuthenticationService() {
        this.authAPI = SPSClient.getClient().create(AuthAPI.class);
    }

    public static AuthenticationService getInstance() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService();
        }
        return authenticationService;
    }

    public Single<Response<AuthenticationResponse>> singIn(LoginDTO loginDTO) {
        return this.authAPI.singIn(loginDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<Void>> singUp(UserDTO userDTO) {
        return this.authAPI.singUp(userDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
