package com.booking.integration.controllers;

import com.booking.models.Role;
import com.booking.models.User;
import com.booking.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

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
    private static User user1;
    private static User user2;
    private static User user3;

    @BeforeAll
    public static void fillInDB() {
        user1 = new User( 1L, "Ivan", "Ivanov", "ivan21", "123s", new HashSet<>(Collections.singletonList(Role.USER)));
        user2 = new User(2L, "Petr", "Petrov", "pert1", "q987", new HashSet<>(Arrays.asList(Role.LECTOR, Role.USER)));
        user3 = new User(1L, "Nikita", "Ivanov", "pert1", "12s", new HashSet<>(Collections.singletonList(Role.USER)));
    }

//    @AfterEach
//    public void resetDb(List<User> userList) {
//        for (User usr : userList) {
//            userRepo.findById(usr.getId()).ifPresent(dropUser -> userRepo.delete(dropUser));
//        }
//    }

    @Test
    public void createUser() throws Exception {
//        System.out.println(objectMapper.writeValueAsString(user1));
//        mockMvc.perform(post("/users/registration")
//                        .content(objectMapper.writeValueAsString(user1))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.firstname").value(user1.getFirstname()))
//                .andExpect(jsonPath("$.secondname").value(user1.getSecondname()))
//                .andExpect(jsonPath("$.password").value(user1.getPassword()))
//                .andExpect(jsonPath("$.login").value(user1.getLogin()))
//                .andExpect(jsonPath("$.roles").value(user1.getRoles()))
//                .andExpect(jsonPath("$.accountNonLocked").value(user1.getRoles()))
//                .andExpect(jsonPath("$.username").value(user1.getLogin()))
                ;
//                .andExpect(jsonPath("$.type").value(strType));

    }

}
