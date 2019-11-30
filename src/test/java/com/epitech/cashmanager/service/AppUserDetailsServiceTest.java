package com.epitech.cashmanager.service;

import com.epitech.cashmanager.model.Role;
import com.epitech.cashmanager.model.User;
import com.epitech.cashmanager.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AppUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppUserDetailsService appUserDetailsService;

    @Before
    public void init() {
        Role role = new Role();
        role.setRoleName("STANDARD");
        role.setDescription("standard user");
        role.setId(0L);

        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ADMIN");
        roleAdmin.setDescription("Admin user");
        roleAdmin.setId(1L);

        User john = new User();
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setUsername("john.doe");
        john.setPassword("password");
        john.setRoles(new ArrayList<>(List.of(role)));

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setUsername("admin.admin");
        admin.setPassword("password");
        admin.setRoles(new ArrayList<>(List.of(role, roleAdmin)));

        when(userRepository.findByUsername("john.doe")).thenReturn(john);
        when(userRepository.findByUsername("admin.admin")).thenReturn(admin);
    }

    @Test
    public void loadUserByUsernameTest() {

        try {
            appUserDetailsService.loadUserByUsername("unknown.user");
            fail("The method should throw an exception");
        } catch (UsernameNotFoundException e) {
            // An exception should be throw because the username doe's not exist
            assertNotNull(e);
        }

        UserDetails userDetails = appUserDetailsService.loadUserByUsername("john.doe");

        assertNotNull("user is loaded", userDetails);
        assertEquals("john has 1 role", 1, userDetails.getAuthorities().size());

        userDetails = appUserDetailsService.loadUserByUsername("admin.admin");

        assertNotNull("user is loaded", userDetails);
        assertEquals("admin has 2 roles", 2, userDetails.getAuthorities().size());
    }
}
