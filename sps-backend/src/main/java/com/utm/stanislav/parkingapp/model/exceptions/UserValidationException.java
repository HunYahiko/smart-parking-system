package com.utm.stanislav.parkingapp.model.exceptions;

import com.utm.stanislav.parkingapp.model.enums.ValidationInputField;
import lombok.Data;

@Data
public class UserValidationException extends ValidationException {
    
    private ValidationInputField inputField;
    
    public UserValidationException() {
    }
    
    public UserValidationException(String message, ValidationInputField field) {
        super(message);
        this.inputField = field;
    }
}
