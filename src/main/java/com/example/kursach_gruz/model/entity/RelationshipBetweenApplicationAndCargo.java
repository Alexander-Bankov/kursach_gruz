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
public class RelationshipBetweenApplicationAndCargo implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relationship_betwee_application_and_cargo")
    private Long idRelationship;

    @Column(name = "id_application")
    private Long applicationId;

    @JoinColumn(name = "cargo_id")
    private Long cargoId;

}
