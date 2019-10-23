package com.epitech.cashmanager.service;

import com.epitech.cashmanager.dao.UserDao;
import com.epitech.cashmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
public class AppUserDetailsService implements UserDetailsService {

     @Autowired
     private UserDao userRepository;

     @Override
     public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

         User user = userRepository.findByUsername(s);

         if(user == null) {
             throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
         }

         List<GrantedAuthority> authorities = new ArrayList<>();

         user.getRoles().forEach(role -> {
             authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
         });

         UserDetails userDetails = new org.springframework.security.core.userdetails.
                 User(user.getUsername(), user.getPassword(), authorities);

         return userDetails;
     }
 } 