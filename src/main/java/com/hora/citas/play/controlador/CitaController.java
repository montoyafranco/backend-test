package com.hora.citas.play.controlador;

import com.hora.citas.play.entity.Cita;
import com.hora.citas.play.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/citas")
    public ResponseEntity<List<Cita>> getAllCitas() {
        try {
            List<Cita> citas = citaService.getAllCitas();
            if (citas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(citas);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/citas/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Long id) {
        try {
            Cita cita = citaService.getCitaById(id);
            if (cita == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(cita);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/citas")
    public ResponseEntity<Cita> createCita(@RequestBody Cita cita) {
        try {
            Cita newCita = citaService.saveCita(cita);
            return ResponseEntity.created(URI.create("/api/citas/" + newCita.getId())).body(newCita);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/citas/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita cita) {
        try {
            Cita updatedCita = citaService.updateCita(id, cita);
            if (updatedCita == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedCita);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/citas/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        try {
            citaService.deleteCitaById(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
