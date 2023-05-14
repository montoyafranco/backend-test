package com.hora.citas.play.repository;

import com.hora.citas.play.entity.Cita;
import com.hora.citas.play.entity.Doctor;
import com.hora.citas.play.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query(value = "SELECT c.paciente FROM Cita c WHERE c.doctor.id = :doctorId")
    List<Paciente> findAllPacientesByDoctorId(@Param("doctorId") Long doctorId);
}
