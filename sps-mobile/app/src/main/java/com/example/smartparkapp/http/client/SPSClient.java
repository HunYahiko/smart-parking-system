package com.example.smartparkapp.http.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.smartparkapp.http.SessionManager;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SPSClient {
    private static final String API_VERSION = "v1/api/";
    private static final String SERVER_URL = "http://192.168.0.54:8080/" + API_VERSION;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static Retrofit client;

    public static Retrofit getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(createRequestInterceptor());
        if (client == null) {
            client = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(SERVER_URL)
                    .client(httpClient.build())
                    .build();
        }
        return client;
    }

    private static Interceptor createRequestInterceptor() {
        return (chain -> {
            Log.i("createRequestInterceptor", chain.request().url().toString());
            String authToken = SessionManager.getInstance().getAuthToken();
            Request.Builder requestBuilder = chain.request().newBuilder();
            if (authToken != null) {
                requestBuilder
                        .addHeader(AUTHORIZATION_HEADER, "Bearer " + authToken)
                        .build();
            }
            return chain.proceed(requestBuilder.build());
        });
    }
}
