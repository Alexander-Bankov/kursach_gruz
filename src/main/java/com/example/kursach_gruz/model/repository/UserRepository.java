package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //поиск по mail
    @Query("SELECT u FROM User u WHERE u.mail = :email")
    Optional<User> findByEmail(String email);

    //поиск id по mail
    @Query("SELECT u.userId FROM User u WHERE u.mail = :email")
    Optional<Long> findIdByEmail(String email);

    //поиск роли по mail
    @Query("SELECT u.role FROM User u WHERE u.mail = :email")
    Optional<String> findRoleByEmail(String email);
}
