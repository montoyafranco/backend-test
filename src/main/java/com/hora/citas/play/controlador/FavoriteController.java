package com.hora.citas.play.controlador;

import com.hora.citas.play.entity.Doctor;
import com.hora.citas.play.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;

@RequestMapping("/api")
@RestController
public class FavoriteController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) throws SQLException {
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            if (doctor == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(doctor);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
    }

    @PostMapping("/doctor/")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) throws SQLException {
        try {






            Doctor newDoctor = doctorService.createDoctor(doctor);
            return ResponseEntity.created(URI.create("/doctors/" + newDoctor.getId())).body(newDoctor);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
    }

    @PutMapping("/doctor/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) throws SQLException {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
            if (updatedDoctor == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedDoctor);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
    }

    @DeleteMapping("/doctor/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) throws SQLException {
        try {
            boolean deleted = doctorService.deleteDoctor(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
    }
}

