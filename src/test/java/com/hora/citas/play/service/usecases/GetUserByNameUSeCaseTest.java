package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetUserByNameUSeCaseTest {

    @Test
    void findByUsernameShouldReturnUser() {
        // Arrange
        String username = "john_doe";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        GetUserByNameUSeCase getUserByNameUSeCase = new GetUserByNameUSeCase(userRepository);

        // Act
        User result = getUserByNameUSeCase.findByUsername(username);

        // Assert
        assertEquals(expectedUser, result);
        verify(userRepository, times(1)).findByUsername(username);
    }
}
