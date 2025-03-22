package com.example.kursach_gruz.model.dto.showdto;

import com.example.kursach_gruz.model.dto.BaseDTO;
import com.example.kursach_gruz.model.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserShowDTO implements BaseDTO {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}
