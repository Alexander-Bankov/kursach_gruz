package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.dto.UserDTO;
import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.repository.UserRepository;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalInfoService {

    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public UserShowDTO getCurrentUserInfo() {
        String mail = authorizationService.getCurrentUserEmail();

        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Преобразовываем User в UserShowDTO
        return new UserShowDTO(user.getFullName(), user.getMail(), user.getPassword(), user.getPhone(), user.getRole());
    }

    public UserShowDTO updateUserInfo(UserDTO userDTO) {
        String mail = authorizationService.getCurrentUserEmail();

        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Обновляем поля пользователя
        user.setFullName(userDTO.getFullName());
        user.setMail(userDTO.getEmail());
        user.setPhone(userDTO.getPhoneNumber());

        userRepository.save(user);

        return new UserShowDTO(user.getFullName(), user.getMail(), user.getPassword(), user.getPhone(), user.getRole());
    }
}
