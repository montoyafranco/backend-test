package com.hora.citas.play.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String email;
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas;

    // Constructores, getters y setters
}
