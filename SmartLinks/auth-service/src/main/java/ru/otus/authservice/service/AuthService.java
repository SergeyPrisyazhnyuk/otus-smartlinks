package ru.otus.authservice.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.authservice.model.User;
import ru.otus.authservice.repository.UserRepository;

import java.util.List;

@Service
@Builder
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(User user) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        return dbUser != null && dbUser.getPassword().equals(user.getPassword());
    }

    public String addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            log.info("User with name : " + user.getUsername() + " - already exists");
            return "User with name : " + user.getUsername() + " - already exists";
        } else {
            return userRepository.save(user).toString();
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}