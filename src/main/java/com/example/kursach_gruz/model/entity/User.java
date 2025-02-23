package com.example.kursach_gruz.model.entity;

import com.example.kursach_gruz.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Long userId;

    @Column(name = "fio")
    private String fullName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name ="role")
    private Role role;

    public User(Long id) {
        this.userId = id;
    }
}