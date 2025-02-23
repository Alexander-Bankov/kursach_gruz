package com.example.kursach_gruz.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cargo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cargo implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Long idCargo;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
