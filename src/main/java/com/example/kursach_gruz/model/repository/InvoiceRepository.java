package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "SELECT * FROM invoice i WHERE i.application_id = :applicationId", nativeQuery = true)
    Optional<Invoice> findInvoiceByApplicationId(@Param("applicationId") Long applicationId);

    @Query(value = "SELECT i.* FROM invoice i " +
                   "INNER JOIN application ap ON i.application_id = ap.id_application " +
                   "WHERE ap.id_user = :userId", nativeQuery = true)
    Optional<List<Invoice>> findInvoicesByUserId(@Param("userId") Long userId);
}
