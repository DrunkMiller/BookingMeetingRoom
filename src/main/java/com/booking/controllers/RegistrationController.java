package com.booking.controllers;


import com.booking.advice.EntityAlreadyExistException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.models.User;
import com.booking.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;


    @PostMapping(value = "/registration")
    public ResponseEntity<User> addUser(@Valid User user) {
        User userFromDb = userRepo.findByLogin(user.getLogin());
        if (userFromDb != null) {
            throw new EntityAlreadyExistException("An employee with this login already exist!");
        }
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }


}
