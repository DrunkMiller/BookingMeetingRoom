package com.booking.integration.controllers;

import com.booking.advice.ResourceNotFoundException;
import com.booking.models.TypeEvent;
import com.booking.repositories.TypeEventRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class TypeEventControllerIntegrationTest {
    private final String strType = "Yoga123";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TypeEventRepo typeEventRepo;

    @AfterEach
    public void resetDb() {
        TypeEvent event =typeEventRepo.findByType(strType);
        if (event !=null) {
            typeEventRepo.delete(event);
        }
        TypeEvent event1 =typeEventRepo.findByType(strType+"456");
        if (event1 !=null) {
            typeEventRepo.delete(event1);
        }
    }

    @Test
    public void createEvent() throws Exception {
        TypeEvent typeEvent = new TypeEvent();
        typeEvent.setType(strType);
        mockMvc.perform(post("/types/type")
                        .content(objectMapper.writeValueAsString(typeEvent))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.type").value(strType));
    }

    @Test
    public void getEventByIdWhichExist() throws Exception{
        Long id = createTypeEvent(strType).getId();
        mockMvc.perform(
                get("/types/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.type").value(strType));

    }

    @Test
    public void getEventByIdWhichNotExist() throws Exception{
        mockMvc.perform(get("/types/-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Type event with ID number '-1' does not exist", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(jsonPath("$.message").value("Entity not found exception"));
    }

    @Test
    public void updateEvent() throws Exception {
        Long id = createTypeEvent(strType).getId();
        TypeEvent eventForUpdate = new TypeEvent();
        eventForUpdate.setType(strType +"456");
        mockMvc.perform(
                        put("/types/{id}", id).content(objectMapper.writeValueAsString(eventForUpdate))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Event Update Successfully']").value("true"));
    }

    @Test
    public void deleteEvent() throws Exception {
        Long id = createTypeEvent(strType).getId();
        mockMvc.perform(delete("/types/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Event Deleted Successfully']").value("true"));
    }

    private TypeEvent createTypeEvent(String type) {
        TypeEvent typeEvent = new TypeEvent();
        typeEvent.setType(type);
        return typeEventRepo.save(typeEvent);
    }
}