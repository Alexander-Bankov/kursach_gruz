package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //поиск по mail
    @Query("SELECT u FROM User u WHERE u.mail = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT new com.example.kursach_gruz.model.dto.showdto.UserShowDTO(u.fullName, u.mail, u.password, u.phone, u.role) FROM User u WHERE u.mail = :email")
    Optional<UserShowDTO> findUserShowDTOByEmail(String email);

    @Query("SELECT new com.example.kursach_gruz.model.dto.showdto.UserShowDTO(u.fullName, u.mail, u.password, u.phone, u.role) FROM User u")
    List<UserShowDTO> findUser();

    @Query(value = "SELECT new com.example.kursach_gruz.model.dto.showdto.UserShowDTO(u.fullName, u.mail, u.password, u.phone, u.role) " +
                   "FROM User u " +
                   "WHERE LOWER(u.mail) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<UserShowDTO> findUsersByEmail(@Param("email") String email);

    //поиск id по mail
    @Query("SELECT u.userId FROM User u WHERE u.mail = :email")
    Optional<Long> findIdByEmail(String email);

    //поиск роли по mail
    @Query("SELECT u.role FROM User u WHERE u.mail = :email")
    Optional<String> findRoleByEmail(String email);
}
