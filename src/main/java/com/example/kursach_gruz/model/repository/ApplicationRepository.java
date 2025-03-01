package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByIdApplication(Long idApplication);
}
