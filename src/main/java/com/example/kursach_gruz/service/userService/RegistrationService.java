package com.example.kursach_gruz.service.userService;

import com.example.kursach_gruz.model.dto.RegistrationDTO;
import org.springframework.security.core.userdetails.User;
import com.example.kursach_gruz.model.enums.Role;
import com.example.kursach_gruz.model.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
public class RegistrationService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public Boolean register(RegistrationDTO registrationDto) {
        String a = String.valueOf(userRepository.findByEmail(registrationDto.getEmail()));
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            new RuntimeException("Такой пользователь уже есть");
            return false;
        } else {
            com.example.kursach_gruz.model.entity.User user = registrationDto.convertRegistrationDTOToUser(registrationDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userRepository.save(user);
            return true;
        }
    }
}
