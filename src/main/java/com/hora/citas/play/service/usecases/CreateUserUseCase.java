package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUserAction(User user){
        return userRepository.save(user);
    }
}
