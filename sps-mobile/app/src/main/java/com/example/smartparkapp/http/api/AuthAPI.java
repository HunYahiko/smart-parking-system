package com.example.smartparkapp.http.api;

import com.example.smartparkapp.dto.AuthenticationResponse;
import com.example.smartparkapp.dto.LoginDTO;
import com.example.smartparkapp.dto.UserDTO;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {

    @POST("auth/sing-in")
    Single<Response<AuthenticationResponse>> singIn(@Body LoginDTO loginDTO);

    @POST("users")
    Single<Response<Void>> singUp(@Body UserDTO userDTO);
}
