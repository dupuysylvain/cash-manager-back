package com.epitech.cashmanager.service;

import com.epitech.cashmanager.model.User;

public interface UserService {
    Iterable<User> getAllUsers();
    User createUser(User user);
    User getUser(String username);
}
