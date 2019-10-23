package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.dao.UserDao;
import com.epitech.cashmanager.model.User;
import com.epitech.cashmanager.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public Iterable<User> listUsers(Principal principal) {
        return userDao.findAll();
    }

    /**
     * Create a new user
     * @param newUser
     * @return
     */
    @RequestMapping(value="/api/users", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userDao.save(newUser);
    }
}
