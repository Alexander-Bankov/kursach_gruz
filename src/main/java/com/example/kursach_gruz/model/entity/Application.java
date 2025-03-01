package com.example.kursach_gruz.model.entity;

import com.example.kursach_gruz.model.enums.ApplicationStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Application(Long applicationId) {
        this.idApplication = applicationId;
    }
}
