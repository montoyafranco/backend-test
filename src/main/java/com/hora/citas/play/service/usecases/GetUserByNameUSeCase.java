package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.User;
import com.hora.citas.play.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserByNameUSeCase {

    private UserRepository userRepository;

    public GetUserByNameUSeCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
