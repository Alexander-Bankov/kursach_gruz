package com.example.kursach_gruz.service.userService;

import com.example.kursach_gruz.config.JwtUtil;
import com.example.kursach_gruz.model.dto.AuthorizationDTO;
import com.example.kursach_gruz.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
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
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthorizationService(UserRepository userRepository,  AuthenticationManager authenticationManager,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<Map<String, String>> authorization(AuthorizationDTO authorizationDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            // Выполняем аутентификацию
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authorizationDTO.getEmail(),
                            authorizationDTO.getPassword()
                    )
            );
            // Генерируем JWT токен
            String accessToken = jwtUtil.generateToken(authorizationDTO.getEmail());
            // Получаем роль пользователя
            Optional<String> optionalRole = userRepository.findRoleByEmail(authorizationDTO.getEmail());
            String role = optionalRole.orElse("UNKNOWN");
            response.put("accessToken", accessToken);
            response.put("role", role);
            System.out.println(accessToken);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Неверные учетные данные"));
        }
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Получаем email из аутентификации (если email является именем пользователя)
        }
        throw new IllegalStateException("Пользователь не авторизован");
    }
}
