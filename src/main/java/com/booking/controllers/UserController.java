package com.booking.controllers;

import com.booking.models.User;
import com.booking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/registration")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userNew = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUserById(@PathVariable(value = "id") Long userId) {
        Map<String, Boolean> mapResult = userService.deleteUserById(userId);
        return ResponseEntity.ok(mapResult);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userNew) {
        Map<String, Boolean> mapResult = userService.updateUser(userId, userNew);
        return ResponseEntity.ok(mapResult);
    }

}
