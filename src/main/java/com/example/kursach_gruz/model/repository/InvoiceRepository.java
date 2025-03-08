package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "SELECT * FROM invoice i WHERE i.application_id = :applicationId", nativeQuery = true)
    Optional<Invoice> findInvoiceByApplicationId(@Param("applicationId") Long applicationId);
}
