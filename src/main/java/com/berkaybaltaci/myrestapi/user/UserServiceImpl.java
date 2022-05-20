package com.berkaybaltaci.myrestapi.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
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

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());

        return userDto;
    }

    @Override
    public User dtoToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setId(userDto.getId());

        return user;
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> authoritySet = new HashSet<>();

        User user = userRepository.findUserByName(username);

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                true, true, true, true,
                authoritySet
        );
    }
}
