package com.utm.stanislav.parkingapp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    private String username;
    private String password;
}
