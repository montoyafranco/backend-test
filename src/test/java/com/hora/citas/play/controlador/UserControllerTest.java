package com.hora.citas.play.controlador;

import com.hora.citas.play.controlador.dto.UserDTO;
import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import com.hora.citas.play.service.usecases.CreateUserUseCase;
import com.hora.citas.play.service.usecases.GetUserByNameUSeCase;
import com.hora.citas.play.service.usecases.LoginUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;





@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private GetUserByNameUSeCase getUserByNameUseCase;

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private CreateUserUseCase createUserUseCase;


    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        // Mock the necessary dependencies
        when(createUserUseCase.createUserAction(any(User.class))).thenReturn(new User(1L, "john_doe", "encoded_password"));

        // Create a sample UserDTO
        UserDTO userDTO = new UserDTO(null, "john_doe", "password123");

        // Call the createUser method
        ResponseEntity<Object> response = userController.createUser(userDTO);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        // Check if the response body contains the created user's information
        UserDTO responseDTO = (UserDTO) response.getBody();
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("john_doe", responseDTO.getUsername());
        assertEquals("encoded_password", responseDTO.getPassword());
    }
    @Test
    void testCreateUser_DataAccessException() {
        // Mock the necessary dependencies and throw a DataAccessException
        when(createUserUseCase.createUserAction(any(User.class))).thenThrow(new EmptyResultDataAccessException(1));

        // Create a sample UserDTO
        UserDTO userDTO = new UserDTO(null, "john_doe", "password123");

        // Call the createUser method
        ResponseEntity<Object> response = userController.createUser(userDTO);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error de acceso a datos", response.getBody());
    }

    @Test
    void testCreateUser_Exception() {
        // Mock the necessary dependencies and throw a custom exception
        when(createUserUseCase.createUserAction(any(User.class))).thenThrow(new RuntimeException("Custom exception message"));

        // Create a sample UserDTO
        UserDTO userDTO = new UserDTO(null, "john_doe", "password123");

        // Call the createUser method
        ResponseEntity<Object> response = userController.createUser(userDTO);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Custom exception message", response.getBody());
    }

    @Test
    void testLogin_Successful() {

        User user = new User();
        user.setUsername("john_doe");
        String hashedPassword = new BCryptPasswordEncoder().encode("password123");
        user.setPassword(hashedPassword);
        when(loginUseCase.findByUsername(anyString())).thenReturn(user);


        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("john_doe");
        userDTO.setPassword("password123");

        // Call the login method
        ResponseEntity<Object> response = userController.login(userDTO);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Map);


        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody.get("token"));
        assertNotNull(response.getHeaders().get("Authorization"));

    }


    @Test
    void testGetUser() {
        // Create a sample user
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");

        // Mock the necessary dependencies
        when(getUserByNameUseCase.findByUsername(anyString())).thenReturn(user);

        // Call the getUser method
        ResponseEntity<Object> response = userController.getUser("john_doe");

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDTO userDTO = (UserDTO) response.getBody();
        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }
    @Test

    void testGetUser_DataAccessException() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");

        // Mock the necessary dependencies and throw a DataAccessException
        when(getUserByNameUseCase.findByUsername(anyString())).thenThrow(new EmptyResultDataAccessException(1));


        // Call the getUser method
        ResponseEntity<Object> response = userController.getUser("john_doe");

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error de acceso a datos", response.getBody());
    }


    @Test
    void testGetUser_Exception() {

        when(getUserByNameUseCase.findByUsername(anyString())).thenThrow(new RuntimeException("Custom exception message"));


        ResponseEntity<Object> response = userController.getUser("john_doe");

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }



}