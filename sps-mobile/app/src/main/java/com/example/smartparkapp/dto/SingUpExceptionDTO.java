package com.example.smartparkapp.dto;

import com.example.smartparkapp.model.enums.ValidationInputField;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class SingUpExceptionDTO {
    private final String exceptionMessage;
    private final ValidationInputField inputField;

    public SingUpExceptionDTO(String exceptionMessage, ValidationInputField inputField) {
        this.exceptionMessage = exceptionMessage;
        this.inputField = inputField;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public ValidationInputField getInputField() {
        return inputField;
    }

    public static SingUpExceptionDTO from(ResponseBody responseBody) {
        Gson gson = new Gson();
        Type type = new TypeToken<SingUpExceptionDTO>(){}.getType();
        return gson.fromJson(responseBody.charStream(), type);
    }
}
