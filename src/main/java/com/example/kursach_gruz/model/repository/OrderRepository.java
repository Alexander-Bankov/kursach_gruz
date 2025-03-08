package com.example.kursach_gruz.model.repository;

import com.example.kursach_gruz.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByIdApplication(Long idApplication);
}
