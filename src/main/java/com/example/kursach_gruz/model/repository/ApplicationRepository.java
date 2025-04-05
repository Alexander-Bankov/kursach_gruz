package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.dto.showdto.CargoShowDTO;
import com.example.kursach_gruz.model.dto.showdto.ShowApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByIdApplication(Long idApplication);

    @Query(value = """
        select
            ap.id_application as id,
            ap.create_date as createDate,
            ap.desired_departure_date as desiredDepartureDate,
            ap.desired_date_of_receipt as desiredDateOfReceipt,
            ap.desired_point_of_departure as desiredPointOfDeparture,
            ap.desired_point_of_receipt as desiredPointOfReceipt,
            ap.description as description,
            ap.status::text as applicationStatus
            from application ap
            where ap.id_user = :userId
""", nativeQuery = true)
    List<ShowApplicationDTO> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Application a WHERE a.user = :user")
    List<Application> findAllByUser(@Param("user") User user);

    @Query(value = """
        select id_user from application ap where ap.id_application = :applicationId
""", nativeQuery = true)
 Long findUserIdByApplicationId(@Param("applicationId") Long applicationId);

}
