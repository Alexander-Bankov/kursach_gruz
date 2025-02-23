package com.example.kursach_gruz.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthorizationDTO implements BaseDTO {

    private String email;
    private String password;
}
