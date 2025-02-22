package com.example.kursach_gruz.config;

import com.example.kursach_gruz.service.userService.RegistrationService;
import com.example.kursach_gruz.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Autowired
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Настройка CORS
                .cors(cors->cors.disable())
                // Отключаем CSRF, если это допустимо
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/swagger-ui/index.html").permitAll()
                        .requestMatchers("/gruz/registration").permitAll() // Разрешаем доступ к ресурсу регистрации
                        .requestMatchers("/gruz/authorization").permitAll()
                        .requestMatchers("/html/Authorization.html", "/html/swagger-ui/index.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll() // Разрешаем доступ к ресурсам Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll() // Разрешаем доступ к документации
                        .requestMatchers("/html/Registration.html", /*"/html/**",*/"/app/**", "/image/**","/js/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Создание сессии при необходимости
                        .maximumSessions(1) // Максимальное количество сессий для одного пользователя
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Перенаправление после выхода
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider());

        return http.build(); // Возвращаем построенный объект HttpSecurity
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
