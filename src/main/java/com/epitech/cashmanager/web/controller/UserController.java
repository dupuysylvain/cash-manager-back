package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.dao.UserDao;
import com.epitech.cashmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller of users
 * only ADMIN users can use it
 */
@RestController
@PreAuthorize("hasAuthority('ADMIN_USER')")
public class UserController {

    @Autowired
    private UserDao userDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * get list of all users
     * @return
     */
    @RequestMapping(value="/api/users", method= RequestMethod.GET)
    public Iterable<User> listArticles() {
        return userDao.findAll();
    }

    /**
     * Create a new user
     * @param newUser
     * @return
     */
    @RequestMapping(value="/api/users", method= RequestMethod.POST)
    public User createUser(@RequestBody User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userDao.save(newUser);
    }
}
