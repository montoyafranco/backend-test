package com.hora.citas.play.controlador;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.hora.citas.play.controlador.dto.UserDTO;
import com.hora.citas.play.entity.User;
import com.hora.citas.play.service.usecases.CreateUserUseCase;
import com.hora.citas.play.service.usecases.LoginUseCase;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private LoginUseCase loginUseCase;

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getPacienteById(@PathVariable Long id) {
//        try {
//            Paciente paciente = pacienteService.getPacienteById(id);
//            if (paciente == null) {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.ok(paciente);
//        } catch (DataAccessException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
//        }
//    }


// ...

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        try {
            String encodedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());

            User userEntity = new User(
                    userDTO.getUsername(),
                    encodedPassword);

            User saveUser = createUserUseCase.createUserAction(userEntity);

            UserDTO responseDTO = new UserDTO(
                    saveUser.getId(),
                    saveUser.getUsername(),
                    saveUser.getPassword());

            return ResponseEntity.created(URI.create("/api/user/" + responseDTO.getId())).body(responseDTO);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO userDTO) {
        try {

            User user = loginUseCase.findByUsername(userDTO.getUsername());

            if (user != null && new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
                byte[] keyBytes = new byte[256 / 8];
                SecureRandom secureRandom = new SecureRandom();
                secureRandom.nextBytes(keyBytes);
                SecretKey key = Keys.hmacShaKeyFor(keyBytes);
                String token = Jwts.builder()
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000L))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

                return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Login exitoso");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

