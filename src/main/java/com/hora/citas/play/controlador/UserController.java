package com.hora.citas.play.controlador;

import com.hora.citas.play.service.usecases.GetUserByNameUSeCase;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);



    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private LoginUseCase loginUseCase;
    @Autowired
    private GetUserByNameUSeCase getUserByNameUSeCase;

    private static final String SECRET_KEY = "montoya_clave_secreta343434343434343434#";



    @PostMapping("/register")
    @Operation(summary = "Create a new user", description = "Endpoint to register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

            logger.info("User created successfully: {}", responseDTO.getId());

            return ResponseEntity.created(URI.create("/api/user/" + responseDTO.getId())).body(responseDTO);
        } catch (DataAccessException e) {
            logger.error("DataAccessException occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Endpoint for user login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> login(@RequestBody UserDTO userDTO) {
        try {
            User user = loginUseCase
                    .findByUsername(userDTO.getUsername());

            if (user != null && new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {

                String token = Jwts.builder()
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000L))
                        .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                        .compact();

                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("token", "Bearer " + token);


                logger.info("User logged in successfully: {}", user.getUsername());

                return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(responseBody);
            } else {
                logger.info("Invalid credentials for user: {}", userDTO.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }
        } catch (DataAccessException e) {
            logger.error("DataAccessException occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/get/{username}")
    @Operation(summary = "Get user by username", description = "Endpoint to retrieve a user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> getUser(@PathVariable String username) {
        try {

            User user = getUserByNameUSeCase.findByUsername(username);

            UserDTO userDTOResponse = new UserDTO(user.getId(),
                    user.getUsername(),
                    user.getPassword());



            logger.info("User retrieved successfully: {}", username);
            return ResponseEntity.ok().body(userDTOResponse);

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

