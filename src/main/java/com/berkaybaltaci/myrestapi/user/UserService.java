package com.berkaybaltaci.myrestapi.user;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Long id);

    User addUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    UserDto entityToDto(User user);

    User dtoToEntity(UserDto userDto);

    User findUserByName(String username);
}
