package com.example.smartparkapp.authentication.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartparkapp.authentication.AuthenticationService;
import com.example.smartparkapp.dto.SingUpExceptionDTO;
import com.example.smartparkapp.dto.UserDTO;
import com.example.smartparkapp.model.enums.ValueLessResponse;

import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class RegistrationViewModel extends ViewModel {
    private MutableLiveData<ValueLessResponse> responseLiveData = new MutableLiveData<>();
    private MutableLiveData<SingUpExceptionDTO> signUpExceptionData = new MutableLiveData<>();

    private AuthenticationService authenticationService = AuthenticationService.getInstance();

    public void signUp(UserDTO userDTO) {
        this.authenticationService.singUp(userDTO).subscribe(new SingleObserver<Response<Void>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Response<Void> singUpResponse) {
                if (!singUpResponse.isSuccessful()) {
                    signUpExceptionData.postValue(
                            SingUpExceptionDTO.from(Objects.requireNonNull(singUpResponse.errorBody())));
                } else {
                    responseLiveData.postValue(ValueLessResponse.NO_DATA);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public MutableLiveData<ValueLessResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<SingUpExceptionDTO> getSignUpExceptionData() {
        return signUpExceptionData;
    }
}
