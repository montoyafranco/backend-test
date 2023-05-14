package com.hora.citas.play.controlador;

import com.hora.citas.play.controlador.dto.UserDTO;
import com.hora.citas.play.entity.Paciente;
import com.hora.citas.play.entity.User;
import com.hora.citas.play.service.PacienteService;
import com.hora.citas.play.service.usecases.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPacienteById(@PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.getPacienteById(id);
            if (paciente == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(paciente);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        try {
            User userEntity = new User(userDTO.getUsername(), userDTO.getPassword());

            User saveUser = createUserUseCase.createUserAction(userEntity);

            UserDTO responseDTO = new UserDTO(
                    saveUser.getId(), saveUser.getUsername(),saveUser.getPassword());

            return ResponseEntity.created(URI.create("/api/user/" + responseDTO.getId())).body(responseDTO);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        try {
            Paciente updatedPaciente = pacienteService.updatePaciente(id, paciente);
            if (updatedPaciente == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedPaciente);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaciente(@PathVariable Long id) {
        try {
            boolean deleted = pacienteService.deletePaciente(id);
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

