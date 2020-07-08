package com.example.smartparkapp.http;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFERENCES_NAME = "SESSION_PREFERENCES";
    private static final String TOKEN_KEY = "BEARER_TOKEN";
    private static SessionManager instance;

    private SharedPreferences sharedPreferences;

    private SessionManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public String getAuthToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void setAuthToken(String authToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, authToken);
        editor.apply();
    }
}
