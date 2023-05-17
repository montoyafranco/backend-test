package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class GetUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private GetUserByIdUseCase getUserByIdUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        getUserByIdUseCase = new GetUserByIdUseCase(userRepository);
    }

    @Test
    void testFindUserById_UserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("john_doe");
        user.setPassword("password123");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> result = getUserByIdUseCase.findUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindUserById_UserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<User> result = getUserByIdUseCase.findUserById(userId);

        assertFalse(result.isPresent());
    }

}
