package com.example.kursach_gruz.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "application")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Application implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application")
    private Long idApplication;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "desired_departure_date")
    private LocalDateTime desiredDepartureDate;

    @Column(name = "desired_date_of_receipt")
    private LocalDateTime desiredDateOfReceipt;

    @Column(name = "desired_point_of_departure")
    private String desiredPointOfDeparture;

    @Column(name = "desired_point_of_receipt")
    private String desiredPointOfReceipt;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
