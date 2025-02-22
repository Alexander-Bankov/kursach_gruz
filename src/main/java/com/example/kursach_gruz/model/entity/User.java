package com.example.kursach_gruz.model.entity;

import com.example.kursach_gruz.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

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
}