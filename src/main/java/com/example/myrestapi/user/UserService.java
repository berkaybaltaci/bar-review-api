package com.example.myrestapi.user;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Long id);

    User addUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);
}
