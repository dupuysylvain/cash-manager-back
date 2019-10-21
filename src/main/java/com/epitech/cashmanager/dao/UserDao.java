package com.epitech.cashmanager.dao;

import com.epitech.cashmanager.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
