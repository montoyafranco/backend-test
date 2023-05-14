package com.hora.citas.play.service;

import com.hora.citas.play.entity.Doctor;
import com.hora.citas.play.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctores() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor"));
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor"));
        doctor.setNombre(doctorDetails.getNombre());

        doctor.setEspecialidad(doctorDetails.getEspecialidad());

        return doctorRepository.save(doctor);
    }

    public boolean deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor"));
        doctorRepository.delete(doctor);
        return false;
    }

}

