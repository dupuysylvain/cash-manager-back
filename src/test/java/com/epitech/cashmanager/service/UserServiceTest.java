package com.epitech.cashmanager.service;

import com.epitech.cashmanager.model.User;
import com.epitech.cashmanager.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void init() {
        User john = new User();
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setUsername("john.doe");

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setUsername("admin.admin");

        when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(john, admin)));

        when(userRepository.findByUsername("john.doe")).thenReturn(john);
        when(userRepository.findByUsername("admin.admin")).thenReturn(admin);

        when(userRepository.save(any(User.class))).thenAnswer((Answer<User>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return (User) args[0];
        });
    }

    @Test
    public void getAllUsersTest(){
        ArrayList<User> allUsers = (ArrayList<User>) userService.getAllUsers();

        assertEquals("The size of users is 2", 2, allUsers.size());
        assertEquals("first user is john", "John", allUsers.get(0).getFirstName());
        assertEquals("second user is admin", "Admin", allUsers.get(1).getLastName());
    }

    @Test
    public void createUserTest(){
        User jax = new User();
        jax.setFirstName("Jax");
        jax.setLastName("Sparrow");
        jax.setUsername("jax.sparrow");
        jax.setPassword("password");

        User newUser = userService.createUser(jax);

        assertNotNull("Should return the new user", newUser);
        assertEquals("new user is jax", "jax.sparrow", newUser.getUsername());
        assertNotEquals("password should be encoded", "password", newUser.getPassword());
    }

    @Test
    public void getUserTest(){
        User user = userService.getUser("john.doe");

        assertNotNull("user should be find", user);

        user = userService.getUser("unknown");

        assertNull("Unkown user", user);
    }
}
