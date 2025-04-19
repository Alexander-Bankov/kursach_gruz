package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.UserDTO;
import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.service.impl.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal-info")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService personalInfoService;

    @GetMapping("/current")
    public ResponseEntity<UserShowDTO> getUserInfo() {
        UserShowDTO userShowDTO = personalInfoService.getCurrentUserInfo();
        return ResponseEntity.ok(userShowDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserShowDTO> updateUserInfo(@RequestBody UserDTO userDTO) {
        UserShowDTO updatedUser = personalInfoService.updateUserInfo(userDTO);
        return ResponseEntity.ok(updatedUser);
    }
    @PostMapping("create-order/{invoiceId}")
    public ResponseEntity<String> createInvoice(@PathVariable Long invoiceId) {
        try {
            personalInfoService.createOrderByInvoice(invoiceId);
            return ResponseEntity.ok("Заказ успешно создан");
        } catch (RuntimeException e) {
            // Логирование ошибки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при создании заказа: " + e.getMessage());
        }
    }

    @PostMapping("cancel-all/{invoiceId}")
    public ResponseEntity<String> cancelAll(@PathVariable Long invoiceId) {
        try {
            personalInfoService.cancelAll(invoiceId);
            return ResponseEntity.ok("Все успешно отменено"); // Можно вернуть успешное сообщение
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Возвращаем сообщение из исключения
        }
    }
}
