package com.example.kursach_gruz.model.entity;

import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @Column(name = "id_user_confirmed")
    private Long userConfirmed;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "point_of_departure")
    private String pointOfDeparture;

    @Column(name = "point_of_receipt")
    private String pointOfReceipt;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
}
