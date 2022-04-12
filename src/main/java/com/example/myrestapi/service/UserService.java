package com.example.myrestapi.service;

import com.example.myrestapi.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUser(Long id);
}
