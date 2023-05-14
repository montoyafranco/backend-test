package com.hora.citas.play.service;

import com.hora.citas.play.entity.Cita;
import com.hora.citas.play.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Cita saveCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public Cita getCitaById(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public void deleteCitaById(Long id) {
        citaRepository.deleteById(id);
    }
    public Cita updateCita(Long id, Cita cita) {
        Cita existingCita = getCitaById(id);
        if (existingCita == null) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        existingCita.setFecha(cita.getFecha());
        existingCita.setHora(cita.getHora());
        existingCita.setPaciente(cita.getPaciente());
        existingCita.setDoctor(cita.getDoctor());
        return citaRepository.save(existingCita);
    }

}
