package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
