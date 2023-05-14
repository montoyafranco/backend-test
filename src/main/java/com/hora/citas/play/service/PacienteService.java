package com.hora.citas.play.service;

import com.hora.citas.play.entity.Paciente;
import com.hora.citas.play.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente getPacienteById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    public Paciente createPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente updatePaciente(Long id, Paciente pacienteDetails) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente"));
        paciente.setNombre(pacienteDetails.getNombre());

        return pacienteRepository.save(paciente);
    }

    public boolean deletePaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente"));
        pacienteRepository.delete(paciente);
        return false;
    }

}

