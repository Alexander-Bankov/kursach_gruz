package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.RegistrationDTO;
import com.example.kursach_gruz.model.dto.AuthorizationDTO;
import com.example.kursach_gruz.model.repository.UserRepository;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import com.example.kursach_gruz.service.userService.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gruz")
public class RegistrationController {

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
                    .body("Пользователь успешно зарегистрирован!");
        } else {
            return ResponseEntity.badRequest().body("Такой пользователь уже есть");
        }
    }
    @PostMapping("/authorization")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthorizationDTO authorizationDTO, HttpServletRequest request) {
        return authorizationService.authorization(authorizationDTO, request);
    }



    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        try{
            HttpSession session = request.getSession(false); // Получаем текущую сессию
            String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
            if (mail == null) {
                throw new IllegalStateException("Пользователь не авторизован");
            }
            if (session != null) {
                // Удаляем предыдущую сессию
                session.invalidate();
            }
            return ResponseEntity.ok().body("Logout session");

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("error");
        }
    }
}
