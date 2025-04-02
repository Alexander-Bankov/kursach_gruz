package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.config.TokenBlackList;
import com.example.kursach_gruz.model.dto.JwtAuthenticationResponse;
import com.example.kursach_gruz.model.dto.RegistrationDTO;
import com.example.kursach_gruz.model.dto.AuthorizationDTO;
import com.example.kursach_gruz.model.repository.UserRepository;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import com.example.kursach_gruz.service.userService.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/gruz")
public class RegistrationController {
    @Autowired
    private TokenBlackList tokenBlackList;


    private final UserRepository userRepository;
    private final RegistrationService userRegistrationService;
    private final AuthorizationService authorizationService;
    private final AuthenticationManager authenticationManager;

    public RegistrationController(UserRepository userRepository, RegistrationService userRegistrationService, AuthenticationManager authenticationManager, AuthorizationService authorizationService) {
        this.userRepository = userRepository;
        this.userRegistrationService = userRegistrationService;
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerVolunteer(@RequestBody RegistrationDTO registrationDTO) {
        Boolean us = userRegistrationService.register(registrationDTO);
        if (us) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"message\": \"Пользователь успешно зарегистрирован!\"}");
        } else {
            return ResponseEntity.badRequest().body("Такой пользователь уже есть");
        }
    }
    @PostMapping("/authorization")
    public ResponseEntity<Map<String, String >> login(@RequestBody AuthorizationDTO authorizationDTO) {
        return authorizationService.authorization(authorizationDTO);
    }



    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            String jwt = request.getHeader("Authorization").substring(7); // Извлечение JWT из заголовка

            if (jwt != null) {
                tokenBlackList.blacklistToken(jwt); // Добавляем токен в черный список
            }

            if (session != null) {
                session.invalidate(); // Удаляем сессию
            }
            return ResponseEntity.ok(Map.of("message", "Logout success")); // Возвращаем ответ в формате JSON
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Error during logout"));
        }
    }
}
