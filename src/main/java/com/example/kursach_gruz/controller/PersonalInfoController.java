package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.UserDTO;
import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.service.impl.PersonalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal-info")
@RequiredArgsConstructor
public class PersonalInfoController {
    private final PersonalInfoService personalInfoService;

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
}
