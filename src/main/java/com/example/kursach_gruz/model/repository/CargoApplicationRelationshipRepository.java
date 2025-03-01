package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.dto.showdto.CargoShowDTO;
import com.example.kursach_gruz.model.entity.Cargo;
import com.example.kursach_gruz.model.entity.RelationshipBetweenApplicationAndCargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoApplicationRelationshipRepository extends JpaRepository<RelationshipBetweenApplicationAndCargo, Long> {
    void deleteByCargoId(Long cargoId); // Убедитесь, что ссылку на cargoId

    List<Long> findCargoIdsByApplicationId(Long applicationId); // Этот метод верный

    boolean existsByApplicationIdAndCargoId(Long applicationId, Long cargoId); // Исправленный метод



}
