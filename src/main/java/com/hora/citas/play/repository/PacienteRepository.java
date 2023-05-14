package com.hora.citas.play.repository;

import com.hora.citas.play.entity.Cita;
import com.hora.citas.play.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
