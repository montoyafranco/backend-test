package com.hora.citas.play.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // Constructores, getters y setters
}
