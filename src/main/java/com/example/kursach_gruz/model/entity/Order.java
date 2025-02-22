package com.example.kursach_gruz.model.entity;

import com.example.kursach_gruz.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "date_start_execution")
    private LocalDateTime dateStartExecution;

    @Column(name = "end_date_execution")
    private LocalDateTime endDateExecution;

    @Enumerated(EnumType.STRING)
    @Column(name ="status")
    private Status status;

    @Column(name = "id_application")
    private Long idApplication;

}
