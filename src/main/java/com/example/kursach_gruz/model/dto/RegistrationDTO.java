package com.example.kursach_gruz.model.dto;

import com.example.kursach_gruz.model.entity.User;
import lombok.Data;

@Data
public class RegistrationDTO {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

    public User convertRegistrationDTOToUser(RegistrationDTO registrationDTO){
        User user = new User();
        user.setFullName(registrationDTO.getFullName());
        user.setMail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        user.setPhone(registrationDTO.getPhoneNumber());
        return user;
    }
}
