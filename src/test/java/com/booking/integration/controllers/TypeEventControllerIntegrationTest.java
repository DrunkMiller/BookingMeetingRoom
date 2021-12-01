//package com.booking.integration.controllers;
//
//import com.booking.advice.ResourceNotFoundException;
//import com.booking.models.postgres.TypeEvent;
//import com.booking.repositories.postgres.TypeEventRepo;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Objects;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class TypeEventControllerIntegrationTest {
//    private final String strType = "Yoga123";
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private TypeEventRepo typeEventRepo;
//
//
//    @AfterEach
//    public void resetDb() {
//        TypeEvent event = typeEventRepo.findByType(strType);
//        if (event != null) {
//            typeEventRepo.delete(event);
//        }
//        TypeEvent event1 = typeEventRepo.findByType(strType + "456");
//        if (event1 != null) {
//            typeEventRepo.delete(event1);
//        }
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void createEventForAdmin() throws Exception {
//        TypeEvent typeEvent = new TypeEvent();
//        typeEvent.setType(strType);
//        mockMvc.perform(post("/types/type")
//                        .content(objectMapper.writeValueAsString(typeEvent))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.type").value(strType));
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    public void createEventForUser() throws Exception {
//        TypeEvent typeEvent = new TypeEvent();
//        typeEvent.setType(strType);
//        mockMvc.perform(post("/types/type")
//                        .content(objectMapper.writeValueAsString(typeEvent))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser()
//    public void getEventByIdWhichExist() throws Exception {
//        Long id = createTypeEvent().getId();
//        mockMvc.perform(get("/types/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.type").value(strType));
//    }
//
//    @Test
//    @WithMockUser()
//    public void getEventByIdWhichNotExist() throws Exception {
//        mockMvc.perform(get("/types/-1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
//                .andExpect(result -> assertEquals("Type event with ID number '-1' does not exist", Objects.requireNonNull(result.getResolvedException()).getMessage()))
//                .andExpect(jsonPath("$.message").value("Entity not found exception"));
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void updateEventForAdmin() throws Exception {
//        Long id = createTypeEvent().getId();
//        TypeEvent eventForUpdate = new TypeEvent();
//        eventForUpdate.setType(strType + "456");
//        mockMvc.perform(put("/types/{id}", id).content(objectMapper.writeValueAsString(eventForUpdate))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.type").value(strType + "456"));
//    }
//
//    @Test
//    @WithMockUser(roles = {"LECTOR", "USER"})
//    public void updateEventForUser() throws Exception {
//        Long id = createTypeEvent().getId();
//        TypeEvent eventForUpdate = new TypeEvent();
//        eventForUpdate.setType(strType + "456");
//        mockMvc.perform(put("/types/{id}", id).content(objectMapper.writeValueAsString(eventForUpdate))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void deleteEventForAdmin() throws Exception {
//        Long id = createTypeEvent().getId();
//        mockMvc.perform(delete("/types/{id}", id))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = {"LECTOR", "USER"})
//    public void deleteEventForUser() throws Exception {
//        Long id = createTypeEvent().getId();
//        mockMvc.perform(delete("/types/{id}", id))
//                .andExpect(status().isForbidden());
//    }
//
//    private TypeEvent createTypeEvent() {
//        TypeEvent typeEvent = new TypeEvent();
//        typeEvent.setType(strType);
//        return typeEventRepo.save(typeEvent);
//    }
//}