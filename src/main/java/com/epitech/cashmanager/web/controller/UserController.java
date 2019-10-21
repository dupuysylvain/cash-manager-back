package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.dao.UserDao;
import com.epitech.cashmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="/api/users", method= RequestMethod.GET)
    //@PreAuthorize("hasAuthority('ADMIN_USER')")
    public Iterable<User> listArticles() {
        return userDao.findAll();
    }
}
