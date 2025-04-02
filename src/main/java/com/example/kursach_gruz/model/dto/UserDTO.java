package com.example.kursach_gruz.model.dto;

import lombok.Data;

@Data
public class UserDTO implements BaseDTO{
    private String fullName;
    private String email;
    private String phoneNumber;
}
