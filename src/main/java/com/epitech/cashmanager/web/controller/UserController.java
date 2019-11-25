package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.repository.UserRepository;
import com.epitech.cashmanager.model.User;
import com.epitech.cashmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * controller of users
 * only ADMIN users can use it
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * get list of all users
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value="/api/users", method= RequestMethod.GET)
    public Iterable<User> listUsers(Principal principal) {
        return userService.getAllUsers();
    }

    /**
     * Create a new user
     * @param newUser
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value="/api/users", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @RequestMapping(value="/api/users/me", method= RequestMethod.GET)
    public User getCurrentUser(Principal principal) {
        return userService.getUser(principal.getName());
    }
}
