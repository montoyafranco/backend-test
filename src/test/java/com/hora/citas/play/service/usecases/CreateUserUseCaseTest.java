package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    public void testCreateUserAction() {
        // Arrange
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("john.doe");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        // Act
        User createdUser = createUserUseCase.createUserAction(user);

        // Assert
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        assertNotNull(createdUser);
    }
}
