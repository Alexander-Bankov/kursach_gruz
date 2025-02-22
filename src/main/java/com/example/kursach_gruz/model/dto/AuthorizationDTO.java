package com.example.kursach_gruz.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthorizationDTO {

    private String email;
    private String password;
}
