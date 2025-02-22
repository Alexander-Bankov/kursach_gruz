package com.example.kursach_gruz.service.userService;

import com.example.kursach_gruz.model.dto.AuthorizationDTO;
import com.example.kursach_gruz.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.kursach_gruz.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthorizationService(UserRepository userRepository,  AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<Map<String, String>> authorization(AuthorizationDTO authorizationDTO, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            HttpSession existingSession = request.getSession(false);
            if (existingSession != null) {
                // Удаляем предыдущую сессию
                existingSession.invalidate();
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authorizationDTO.getEmail(),
                            authorizationDTO.getPassword()
                    )
            );

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("userEmail", authorizationDTO.getEmail());

            // Получаем роль пользователя
            Optional<String> optionalRole = userRepository.findRoleByEmail(authorizationDTO.getEmail());
            String role = optionalRole.orElse("UNKNOWN");

            // Заполняем ответ
            response.put("message", "Авторизация успешна");
            response.put("role", role);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Неверные учетные данные"));
        }
    }
}
