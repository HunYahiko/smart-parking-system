package com.utm.stanislav.parkingapp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utm.stanislav.parkingapp.model.enums.ValidationInputField;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignUpExceptionDto {
    private final String exceptionMessage;
    @JsonProperty("inputField")
    private final String field;
}
