package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByIdApplication(Long idApplication);

    @Query(value = "SELECT o.* FROM orders o " +
                   "INNER JOIN application ap ON o.id_application = ap.id_application " +
                   "WHERE ap.id_user = :userId", nativeQuery = true)
    Optional<List<Order>> findOrdersByUserId(@Param("userId") Long userId);
}
