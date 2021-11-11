package com.booking.integration.controllers;

import com.booking.advice.EntityAlreadyExistException;
import com.booking.models.Role;
import com.booking.models.User;
import com.booking.repositories.RoleRepo;
import com.booking.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    private User user1;
    private User user2;
    private User user3;
    private List<User> userList;


    @BeforeEach
    public void fillInDB() {
        Role admin = roleRepo.findByName("ROLE_ADMIN");
        Role user = roleRepo.findByName("ROLE_USER");
        Role lector = roleRepo.findByName("ROLE_LECTOR");
        user1 = new User("Ivan", "Ivanov", "ivan21", "123s", new ArrayList<>(Collections.singletonList(admin)));
        user1.setId(101L);
        user2 = new User("Petr", "Petrov", "pert1", "q987", new ArrayList<>(Arrays.asList(lector, user)));
        user2.setId(102L);
        user3 = new User("Nikita", "Ivanov", "pert1", "12s", new ArrayList<>(Collections.singletonList(user)));
        user3.setId(103L);
        userList = new ArrayList<>(Arrays.asList(user1, user2, user3));
    }

    @AfterEach
    public void resetDb() {
        for (User user : userList) {
            User userDrop = userRepo.findByUsername(user.getUsername());
            if (userDrop != null) {
                userRepo.delete(userDrop);
            }
        }
    }

    @Test
    @WithAnonymousUser
    public void createUser() throws Exception {
        mockMvc.perform(post("/users/registration")
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user1.getLastName()))
                .andExpect(jsonPath("$.username").value(user1.getUsername()));

    }

    @Test
    @WithAnonymousUser
    public void createUserIfExist() throws Exception {
        userRepo.save(user2);
        mockMvc.perform(post("/users/registration")
                        .content(objectMapper.writeValueAsString(user3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityAlreadyExistException))
                .andExpect(result -> assertEquals("An employee with this login: '" + user3.getUsername() + "' already exist!", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(jsonPath("$.message").value("Entity already exist exception"));
    }

    @Test
    @WithMockUser(username = "ivan21")
    public void getUserByIdWhichExistForUser() throws Exception {
        userRepo.save(user1);
        Long id = userRepo.findByUsername(user1.getUsername()).getId();
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user1.getLastName()))
                .andExpect(jsonPath("$.username").value(user1.getUsername()))
                .andExpect(jsonPath("$.password").value(user1.getPassword()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllUsersForAdmin() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getAllUsersForUser() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }


}
