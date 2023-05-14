package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

@Service
public class LoginUseCase {

    private UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsernameAndPassword(User user){
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
