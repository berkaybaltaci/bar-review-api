package com.example.myrestapi.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElse(null);
    }
}
