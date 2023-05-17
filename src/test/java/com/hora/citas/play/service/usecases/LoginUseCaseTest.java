package com.hora.citas.play.service.usecases;


import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginUseCaseTest {

    @Test
    void findByUsernameShouldReturnUser() {

        String username = "john_doe";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        LoginUseCase loginUseCase = new LoginUseCase(userRepository);


        User result = loginUseCase.findByUsername(username);


        assertEquals(expectedUser, result);
        verify(userRepository).findByUsername(username);
    }
}
