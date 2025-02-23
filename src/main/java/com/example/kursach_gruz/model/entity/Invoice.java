package com.example.kursach_gruz.model.entity;

import com.example.kursach_gruz.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Invoice implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Long idInvoice;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "description_invoice")
    private String descriptionInvoice;

    @Column(name = "document")
    private String document;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_user_confirmed", nullable = false)
    private User userConfirmed;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "point_of_departure")
    private String pointOfDeparture;

    @Column(name = "point_of_receipt")
    private String pointOfReceipt;

    @ManyToOne
    @JoinColumn(name = "id_application", nullable = false)
    private Application application;
}
