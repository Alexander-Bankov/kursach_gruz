package com.example.kursach_gruz.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "relationship_betwee_application_and_cargo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelationshipBetweenApplicationAndCargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relationship_betwee_application_and_cargo")
    private Long idRelationship;

    @ManyToOne
    @JoinColumn(name = "id_application", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;

}
