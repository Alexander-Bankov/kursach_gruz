package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.dto.showdto.CargoShowDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.relationship_betwee_application_and_cargo WHERE cargo_id = :cargoId", nativeQuery = true)
    void deleteRelationshipsByCargoId(@Param("cargoId") Long cargoId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.cargo WHERE cargo_id = :cargoId", nativeQuery = true)
    void deleteByCargoId(@Param("cargoId") Long cargoId);

    @Query(value = "SELECT " +
                   "    c.cargo_id AS \"cargoId\", " +
                   "    c.weight AS \"weight\", " +
                   "    c.length AS \"length\", " +
                   "    c.width AS \"width\", " +
                   "    c.height AS \"height\", " +
                   "    c.content AS \"content\", " +
                   "    r.id_application AS \"applicationId\", " +
                   "    c.id_user AS \"userId\" " +
                   "FROM cargo c " +
                   "LEFT JOIN relationship_betwee_application_and_cargo r ON c.cargo_id = r.cargo_id " +
                   "WHERE r.id_application = :applicationId", nativeQuery = true)
    List<CargoShowDTO> findCargoShowByApplicationId(@Param("applicationId") Long applicationId);

    @Query(value = "SELECT " +
                   "    c.cargo_id AS \"cargoId\", " +
                   "    c.weight AS \"weight\", " +
                   "    c.length AS \"length\", " +
                   "    c.width AS \"width\", " +
                   "    c.height AS \"height\", " +
                   "    c.content AS \"content\", " +
                   "    r.id_application AS \"applicationId\", " +
                   "    c.id_user AS \"userId\" " +
                   "FROM cargo c " +
                   "LEFT JOIN relationship_betwee_application_and_cargo r ON c.cargo_id = r.cargo_id " +
                   "WHERE r.cargo_id = :cargoId", nativeQuery = true)
    CargoShowDTO findCargoShowByCargoId(@Param("cargoId") Long cargoId);

}
