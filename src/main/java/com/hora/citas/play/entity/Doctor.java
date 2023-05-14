package com.hora.citas.play.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String especialidad;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas;

    // Constructores, getters y setters
}
